var appDrive =
/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId]) {
/******/ 			return installedModules[moduleId].exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			i: moduleId,
/******/ 			l: false,
/******/ 			exports: {}
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.l = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// define getter function for harmony exports
/******/ 	__webpack_require__.d = function(exports, name, getter) {
/******/ 		if(!__webpack_require__.o(exports, name)) {
/******/ 			Object.defineProperty(exports, name, { enumerable: true, get: getter });
/******/ 		}
/******/ 	};
/******/
/******/ 	// define __esModule on exports
/******/ 	__webpack_require__.r = function(exports) {
/******/ 		if(typeof Symbol !== 'undefined' && Symbol.toStringTag) {
/******/ 			Object.defineProperty(exports, Symbol.toStringTag, { value: 'Module' });
/******/ 		}
/******/ 		Object.defineProperty(exports, '__esModule', { value: true });
/******/ 	};
/******/
/******/ 	// create a fake namespace object
/******/ 	// mode & 1: value is a module id, require it
/******/ 	// mode & 2: merge all properties of value into the ns
/******/ 	// mode & 4: return value when already ns object
/******/ 	// mode & 8|1: behave like require
/******/ 	__webpack_require__.t = function(value, mode) {
/******/ 		if(mode & 1) value = __webpack_require__(value);
/******/ 		if(mode & 8) return value;
/******/ 		if((mode & 4) && typeof value === 'object' && value && value.__esModule) return value;
/******/ 		var ns = Object.create(null);
/******/ 		__webpack_require__.r(ns);
/******/ 		Object.defineProperty(ns, 'default', { enumerable: true, value: value });
/******/ 		if(mode & 2 && typeof value != 'string') for(var key in value) __webpack_require__.d(ns, key, function(key) { return value[key]; }.bind(null, key));
/******/ 		return ns;
/******/ 	};
/******/
/******/ 	// getDefaultExport function for compatibility with non-harmony modules
/******/ 	__webpack_require__.n = function(module) {
/******/ 		var getter = module && module.__esModule ?
/******/ 			function getDefault() { return module['default']; } :
/******/ 			function getModuleExports() { return module; };
/******/ 		__webpack_require__.d(getter, 'a', getter);
/******/ 		return getter;
/******/ 	};
/******/
/******/ 	// Object.prototype.hasOwnProperty.call
/******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "./";
/******/
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(__webpack_require__.s = 0);
/******/ })
/************************************************************************/
/******/ ({

/***/ "./src/appDrive.ts":
/*!*************************!*\
  !*** ./src/appDrive.ts ***!
  \*************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
eval("__webpack_require__.r(__webpack_exports__);\nvar AppDrive = /** @class */ (function () {\n    function AppDrive() {\n    }\n    /**\n     * @description 通过相机获取图片\n     * @param {appDrive.IGetPictureByCameraExtension} extension 扩展参数\n     * @returns {PromiseLike<string>} 返回 base64 的字符串\n     */\n    AppDrive.prototype.getPictureByCamera = function (extension) {\n        var _this = this;\n        if (extension === void 0) { extension = {\n            clip: false,\n            editor: false,\n            compression: 0.9,\n            proportion: 0,\n        }; }\n        return new Promise(function (resolve, reject) {\n            _this.callAppFunction(resolve, reject, 'getPictureByCamera', extension);\n        });\n    };\n    AppDrive.prototype.callAppFunction = function (resolve, reject, funcName, extension) {\n        if (extension === void 0) { extension = {}; }\n        // 回调方法名\n        var callBackName = \"AppCall\" + new Date().getTime() + ~~(Math.random() * 1000);\n        // 绑定临时方法到 window 上\n        window[callBackName] = function () {\n            var arg = [];\n            for (var _i = 0; _i < arguments.length; _i++) {\n                arg[_i] = arguments[_i];\n            }\n            if (arg[0]) {\n                return reject(arg[0]);\n            }\n            resolve.apply(void 0, arg);\n            delete window[callBackName];\n        };\n        var func = {};\n        // 如果是 ios\n        if (window.webkit) {\n            func = window.webkit.messageHandlers;\n            func[funcName].postMessage([callBackName, extension]);\n        }\n        // 如果是 android\n        if (window.android) {\n            console.log('window.android');\n   func = android.getMessage.bind(android);\n            func(callBackName, extension);\n        }\n        throw new Error('原生没有定义');\n    };\n    return AppDrive;\n}());\n/* harmony default export */ __webpack_exports__[\"default\"] = (new AppDrive());\n\n\n//# sourceURL=webpack://appDrive/./src/appDrive.ts?");

/***/ }),

/***/ 0:
/*!*******************************!*\
  !*** multi ./src/appDrive.ts ***!
  \*******************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

eval("module.exports = __webpack_require__(/*! ./src/appDrive.ts */\"./src/appDrive.ts\");\n\n\n//# sourceURL=webpack://appDrive/multi_./src/appDrive.ts?");

/***/ })

/******/ });