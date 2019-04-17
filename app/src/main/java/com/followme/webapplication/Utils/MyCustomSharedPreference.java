package com.followme.webapplication.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.ArrayMap;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class MyCustomSharedPreference implements SharedPreferences {

    private String TAG = MyCustomSharedPreference.class.getSimpleName();
    private static MyCustomSharedPreference instance;
    private SharedPreferences sp;

    public static MyCustomSharedPreference getInstance(WeakReference<Context> contextWeakReference, String dbName){
        if(instance == null){
            synchronized (new Object()){
                instance = new MyCustomSharedPreference(contextWeakReference.get(), dbName);
            }
        }
        return instance;
    }

    private MyCustomSharedPreference(Context context, String dbName){
        sp = context.getSharedPreferences(dbName, MODE_PRIVATE);
    }

    @Override
    public boolean contains(String key) {
        return sp.contains(key);
    }

    public Object getValue(String key, Object value){

        switch (value.getClass().getSimpleName()){
            case "String":
                return getString(key, (String) value);
            case "Float":
                return getFloat(key, (Float) value);
            case "Integer":
                return getInt(key, (Integer) value);
            case "Boolean":
                return getBoolean(key, (Boolean) value);
            case "Long":
                return getLong(key, (Long) value);
            default:
                return "";
        }
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        return sp.getBoolean(key, false);
    }

    @Override
    public int getInt(String key, int defValue) {
        return sp.getInt(key, -1);
    }

    @Override
    public long getLong(String key, long defValue) {
        return sp.getLong(key, 0L);
    }

    @Override
    public float getFloat(String key, float defValue) {
        return sp.getFloat(key, 0F);
    }

    @Override
    public String getString(String key, String defValue) {
        return sp.getString(key, "na");
    }

    @Override
    public Editor edit() {
        return sp.edit();
    }

    public void setValue(String key, Object value){

        switch (value.getClass().getSimpleName()){
            case "String":
                setString(key, (String) value);
                break;
            case "Float":
                setFloat(key, (Float) value);
                break;
            case "Integer":
                setInt(key, (Integer) value);
                break;
            case "Boolean":
                setBoolean(key, (Boolean) value);
                break;
            case "Long":
                setLong(key, (Long) value);
                break;
            default:
                break;
        }
    }

    private void setString(String key, String value){
        LogUtil.logI(TAG, "setString");
        edit().putString(key, value).commit();
    }

    private void setInt(String key, int value){
        LogUtil.logI(TAG, "setInt");
        edit().putInt(key, value).commit();
    }

    private void setFloat(String key, float value){
        LogUtil.logI(TAG, "setFloat");
        edit().putFloat(key, value).commit();
    }

    private void setLong(String key, long value){
        LogUtil.logI(TAG, "setLong");
        edit().putLong(key, value).commit();
    }

    private void setBoolean(String key, boolean value){
        LogUtil.logI(TAG, "setBoolean");
        edit().putBoolean(key, value).commit();
    }

    public void clear(){
        LogUtil.logI(TAG, "clear");
        edit().clear().commit();
    }

    public void remove(String key){
        edit().remove(key).commit();
    }

    @Override
    public Map<String, ?> getAll() {
        return new ArrayMap<String, String>();
    }

    @Override
    public Set<String> getStringSet(String key, Set<String> defValues) {
        return null;
    }

    @Override
    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {

    }

    @Override
    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {

    }
}
