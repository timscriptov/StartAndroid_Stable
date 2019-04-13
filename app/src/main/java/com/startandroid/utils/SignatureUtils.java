package com.startandroid.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Base64;

import org.jetbrains.annotations.NotNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignatureUtils {
    // проверяет подпись приложения
    public static boolean verifySignatureSHA(Context c) {
        return Utils.reverseString(getAPKSignatureSHA(c)).endsWith("w8/9qeZvyqDJ7hL4gYmpaL+ftAk");
    }

    // получает SHA1withRSA подпись приложения
    @NotNull
    public static String getAPKSignatureSHA(@NotNull Context context) {
        String res = "";
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : packageInfo.signatures) {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA");
                messageDigest.update(signature.toByteArray());
                res = Base64.encodeToString(messageDigest.digest(), Base64.DEFAULT);
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException ignored) {

        }
        return res.trim();
    }
}
