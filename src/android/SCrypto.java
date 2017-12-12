package net.xdefine.scrypto;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Method;

public class SCrypto extends CordovaPlugin {

	public CallbackContext callbackContext;
	public static final int REQUEST_READ_PHONE_STATE = 0;

	protected final static String permission = Manifest.permission.READ_PHONE_STATE;

	@Override
	public boolean execute (String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

		this.callbackContext = callbackContext;
		try {
			if (action.equals("getUUID")) {
				if(this.hasPermission(permission)) {
					getDeviceId();
				}
				else {
					this.requestPermission(this, REQUEST_READ_PHONE_STATE, permission);
				}
			}
			else if (action.equals("getModel")) {
			    callbackContext.success(Build.MODEL);
            }
			else if (action.equals("getSecretToken")) {
                callbackContext.success(SecurityHelper.getInstance().getSecretToken(getUUID(), args.getString(0)));
            }
            else {
				this.callbackContext.error("Invalid action");
				return false;
			}
		}
		catch(Exception e ) {
			this.callbackContext.error("Exception occurred: ".concat(e.getMessage()));
			return false;
		}

		return true;
	}


	public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) throws JSONException {
		if(requestCode == REQUEST_READ_PHONE_STATE){
			getDeviceId();
		}
	}

	protected void getDeviceId(){
		try {
			this.callbackContext.success(this.getUUID());
		}catch(Exception e ) {
			this.callbackContext.error("Exception occurred: ".concat(e.getMessage()));
		}
	}

	private String getUUID() {
        Context context = cordova.getActivity().getApplicationContext();
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        String uuid;
        String androidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        String deviceID = tm.getDeviceId();
        String simID = tm.getSimSerialNumber();

        if ("9774d56d682e549c".equals(androidID) || androidID == null) {
            androidID = "";
        }

        if (deviceID == null) {
            deviceID = "";
        }

        if (simID == null) {
            simID = "";
        }

        uuid = androidID + deviceID + simID;
        uuid = String.format("%32s", uuid).replace(' ', '0');
        uuid = uuid.substring(0, 32);
        uuid = uuid.replaceAll("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5");

        return uuid;
    }

	private boolean hasPermission(String permission) throws Exception {
		boolean hasPermission = true;
		Method method = null;
		try {
			method = cordova.getClass().getMethod("hasPermission", permission.getClass());
			Boolean bool = (Boolean) method.invoke(cordova, permission);
			hasPermission = bool.booleanValue();
		} catch (NoSuchMethodException e) {
//			Log.w(TAG, "Cordova v" + CordovaWebView.CORDOVA_VERSION + " does not support API 23 runtime permissions so defaulting to GRANTED for " + permission);
		}
		return hasPermission;
	}

	private void requestPermission(CordovaPlugin plugin, int requestCode, String permission) throws Exception {
		try {
			java.lang.reflect.Method method = cordova.getClass().getMethod("requestPermission", org.apache.cordova.CordovaPlugin.class ,int.class, java.lang.String.class);
			method.invoke(cordova, plugin, requestCode, permission);
		} catch (NoSuchMethodException e) {
			throw new Exception("requestPermission() method not found in CordovaInterface implementation of Cordova v" + CordovaWebView.CORDOVA_VERSION);
		}
	}

}
