"use strict";function _classCallCheck(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}var _createClass=function(){function e(e,t){for(var n=0;n<t.length;n++){var r=t[n];r.enumerable=r.enumerable||!1,r.configurable=!0,"value"in r&&(r.writable=!0),Object.defineProperty(e,r.key,r)}}return function(t,n,r){return n&&e(t.prototype,n),r&&e(t,r),t}}(),AppHook=function(){function e(t){_classCallCheck(this,e),this.platform={is:{android:1===t,ios:2===t,mweb:3===t||1!==t&&2!==t}};var n=this.getArguments("encryptionId");this.logined=n.length>40,window.getDynamicCallBack=this.callback,e.cache("currentAppHookEventName",null)}return _createClass(e,[{key:"callback",value:function(t){console.log(e.cache("currentAppHookEventName"));var n=JSON.parse(t);window.hookCallback(n,e.cache("currentAppHookEventName")),e.cache("currentAppHookEventName",null)}},{key:"share",value:function(t){return e.cache("currentAppHookEventName",params[0]),this.platform.is.android?(Android.ToActivity("inviteFriend",null),!1):this.platform.is.ios?(t.attr("href",this.getMethodUrl("inviteFriend")),!0):void 0}},{key:"getEvent",value:function(t,n,r){var o=t.split("&"),i=o[0],a=o.length>1?this.getAttr(o[1]):"0",l=o.length>2?o.slice(2).join("&"):null;return e.cache("currentAppHookEventName",o[0]),console.log(i,a,l),this.platform.is.android?1==r?(Android.FindGetEvent(i,a,l),!1):(Android.GetEvent(i,a,l),!1):this.platform.is.ios?(n.attr("href",this.getMethodUrl(t,r)),console.log("postUrl:",n.attr("href")),!0):void 0}},{key:"login",value:function(e){var t=void 0;switch(!0){case this.platform.is.android:t=this.androidLogin();break;case this.platform.is.ios:t=this.iosLogin(e)}return t}},{key:"androidLogin",value:function(){return Android.ToActivity("login",null),!1}},{key:"iosLogin",value:function(e){return e.attr("href",this.getMethodUrl("loginIn")),!0}},{key:"getMethodUrl",value:function(e,t,n){switch(t){case 0:return"yrw-skip://invokeMethod="+e;case 1:return"yrw-find://invokeMethod="+e;default:return"yrw://invokeMethod="+e}}},{key:"getArguments",value:function(e,t){var n=t?t.substr(t.indexOf("?")):location.search,r=new RegExp("(^|&)"+e+"=([^&]*)(&|$)"),o=n.substr(1).match(r);return o?decodeURI(o[2]):""}},{key:"getAttr",value:function(e,t){var n=t?t:"=";e.split(n);return e.split(n)[1]}}],[{key:"cache",value:function(e,t){return"undefined"==typeof t?window[e]:(window[e]=t,t)}}]),e}();