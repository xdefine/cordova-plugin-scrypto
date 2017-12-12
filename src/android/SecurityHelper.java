package net.xdefine.helper;

import android.os.Build;

/***
 * 회원의 토큰 암호화 작업 class
 * @author Hongchul Park
 */
public class SecurityHelper {

    protected final static String permission = Manifest.permission.READ_PHONE_STATE;
    
	private static SecurityHelper _instance;
	public static SecurityHelper getInstance() {
		if (_instance == null) _instance = new SecurityHelper();
		return _instance;
	}

	private SecurityHelper() {
		System.loadLibrary("encrypt_token");
	}
	
	private native String genSecretToken(String arg0, String arg1, String arg2);
	private native String genPassword(String arg0);
	
	// public String getDeviceID() {
// 		if(this.hasPermission(permission)){
// 			getDeviceId();
// 		}else{
// 			this.requestPermission(this, REQUEST_READ_PHONE_STATE, permission);
// 		}
// 	}
// 
//     public void onRequestPermissionResult(int requestCode, String[] permissions,
//                                           int[] grantResults) throws JSONException {
//         if(requestCode == REQUEST_READ_PHONE_STATE){
//             getDeviceId();
//         }
//     }
//     
//     protected void getDeviceId(){
//         try {
//             Context context = cordova.getActivity().getApplicationContext();
//             TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
// 
//             String uuid;
//             String androidID = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
//             String deviceID = tm.getDeviceId();
//             String simID = tm.getSimSerialNumber();
// 
//             if ("9774d56d682e549c".equals(androidID) || androidID == null) {
//                 androidID = "";
//             }
// 
//             if (deviceID == null) {
//                 deviceID = "";
//             }
// 
//             if (simID == null) {
//                 simID = "";
//             }
// 
//             uuid = androidID + deviceID + simID;
//             uuid = String.format("%32s", uuid).replace(' ', '0');
//             uuid = uuid.substring(0, 32);
//             uuid = uuid.replaceAll("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5");
// 
//             this.callbackContext.success(uuid);
//         }catch(Exception e ) {
//             this.callbackContext.error("Exception occurred: ".concat(e.getMessage()));
//         }
//     }
// 
//     private boolean hasPermission(String permission) throws Exception{
//         boolean hasPermission = true;
//         Method method = null;
//         try {
//             method = cordova.getClass().getMethod("hasPermission", permission.getClass());
//             Boolean bool = (Boolean) method.invoke(cordova, permission);
//             hasPermission = bool.booleanValue();
//         } catch (NoSuchMethodException e) {
//             Log.w(TAG, "Cordova v" + CordovaWebView.CORDOVA_VERSION + " does not support API 23 runtime permissions so defaulting to GRANTED for " + permission);
//         }
//         return hasPermission;
//     }
// 
//     private void requestPermission(CordovaPlugin plugin, int requestCode, String permission) throws Exception{
//         try {
//             java.lang.reflect.Method method = cordova.getClass().getMethod("requestPermission", org.apache.cordova.CordovaPlugin.class ,int.class, java.lang.String.class);
//             method.invoke(cordova, plugin, requestCode, permission);
//         } catch (NoSuchMethodException e) {
//             throw new Exception("requestPermission() method not found in CordovaInterface implementation of Cordova v" + CordovaWebView.CORDOVA_VERSION);
//         }
//     }
	
	public String getSecretToken() {
	
		return "";	
	
//		return genSecretToken(Build.MODEL, uuid, conf.getGeneratedAt());
	}
	
	public String getEncryptPassword(String password) {
		return this.genPassword(password);
	}

}
