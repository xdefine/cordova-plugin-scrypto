var argscheck = require ('cordova/argscheck')
var exec      = require ('cordova/exec')

module.exports = function () {
	var exports = {}
	
	exports.getVersionName = function (init) {
		var success = (typeof init.success != "undefined") ? init.success : function () {}
		var error   = (typeof init.error   != "undefined") ? init.error   : function () {}
		cordova.exec (success, error, "AppVersion", "getVersionName", [])
	}
	
	exports.getVersionCode = function (init) {
		var success = (typeof init.success != "undefined") ? init.success : function () {}
		var error   = (typeof init.error   != "undefined") ? init.error   : function () {}
		cordova.exec (success, error, "AppVersion", "getVersionCode", [])
	}
	
	return exports;
} ()
