var argscheck = require ('cordova/argscheck')
var exec      = require ('cordova/exec')

module.exports = function () {
	var exports = {}
	
	exports.getUUID = function (init) {
		var success = (typeof init.success != "undefined") ? init.success : function () {}
		var error   = (typeof init.error   != "undefined") ? init.error   : function () {}
		cordova.exec (success, error, "SCrypto", "getUUID", [])
	}
	
	exports.getModel = function (init) {
		var success = (typeof init.success != "undefined") ? init.success : function () {}
		var error   = (typeof init.error   != "undefined") ? init.error   : function () {}
		cordova.exec (success, error, "SCrypto", "getModel", [])
	}
	
	exports.getSecretToken = function (init) {
		var success = (typeof init.success != "undefined") ? init.success : function () {}
		var error   = (typeof init.error   != "undefined") ? init.error   : function () {}
		cordova.exec (success, error, "SCrypto", "getSecretToken", [])
	}
	
	return exports;
} ()
