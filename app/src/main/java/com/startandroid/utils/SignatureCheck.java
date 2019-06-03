package com.startandroid.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Base64;

import org.jetbrains.annotations.NotNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import android.content.pm.*;

public class SignatureCheck {
	
	public native int getSomeValue();
	public native void init(Context ctx)
	
    // проверяет подпись приложения
    public static boolean verifySignature(Context c) {
        return Utils.reverseString(getSignature(c)).endsWith(".RSA");
    }
	
	static {
        System.loadLibrary("startandroid");
    }
	
	public static String getSignature(Context context)
    {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            
			Signature[] signatures = packageInfo.signatures;
			
            return signatures[0].toCharsString();
			
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
