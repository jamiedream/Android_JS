package com.followme.webapplication.OKHttp;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

public class OKHttp {

    private String TAG = OKHttp.class.getSimpleName();

    private X509TrustManager trustManager;

    private final long CONNECT_TIME_OUT = 5L;
    private final long READ_TIME_OUT = 5L;
    private final long WRITE_TIME_OUT = 5L;

    private OkHttpClient okHttpClient;

    private static OKHttp instance;

    OKHttp getInstance(){
        if(instance == null){
            synchronized (new Object()){
                instance = new OKHttp();
            }
        }
        return instance;
    }

    public void setTrustFileStream(InputStream inputStream) throws Exception {

        OkHttpClient.Builder builder =
                new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor());


        SSLSocketFactory factory = getTrustClient(inputStream);
        if(factory != null){
            builder.sslSocketFactory(factory, trustManager).hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        }

    }

    private SSLSocketFactory getTrustClient(InputStream inputStream) throws Exception {
        SSLSocketFactory sslSocketFactory;

        trustManager = trustManagerForCertification(inputStream);
        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, new TrustManager[]{trustManager}, null);
        sslSocketFactory = sslContext.getSocketFactory();
        return sslSocketFactory;
    }

    private X509TrustManager trustManagerForCertification(InputStream inputStream) throws Exception {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(inputStream);
        if(certificates.isEmpty()){
            throw new IllegalArgumentException("expected non-empty set of trusted certificates");
        }
        char[] password = "password".toCharArray();
        KeyStore keyStore = newEmptyKeyStore(password);
        int index = 0;
        for(Certificate certificate: certificates){
            String alias = Integer.toString(index++);
            keyStore.setCertificateEntry(alias, certificate);
        }
        //Build a X509 trust manager
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, password);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if(trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)){
            throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }

    private KeyStore newEmptyKeyStore(char[] password) throws Exception {

        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        InputStream inputStream = null;
        keyStore.load(null, password);
        return keyStore;

    }


}
