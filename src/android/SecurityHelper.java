package net.xdefine.scrypto;

import android.Manifest;
import android.os.Build;

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

	public String getSecretToken(String uuid, String genDate) {
		return genSecretToken(Build.MODEL, uuid, genDate);
	}

	public String getEncryptPassword(String password) {
		return this.genPassword(password);
	}

}
