package com.anjlab.android.iab.v3;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import static com.anjlab.android.iab.v3.data.Constants.ANDROID_TEST_CANCELED;
import static com.anjlab.android.iab.v3.data.Constants.ANDROID_TEST_ITEM_UNAVAILABLE;
import static com.anjlab.android.iab.v3.data.Constants.ANDROID_TEST_PURCHASED;
import static com.anjlab.android.iab.v3.data.Constants.ANDROID_TEST_REFUNDED;
import static com.anjlab.android.iab.v3.data.Constants.IABUTIL_SECURITY;
import static com.anjlab.android.iab.v3.data.Constants.RSA;
import static com.anjlab.android.iab.v3.data.Constants.SHA1WITHRSA;

class Security
{
    private static final String TAG = IABUTIL_SECURITY;
    private static final String KEY_FACTORY_ALGORITHM = RSA;
    private static final String SIGNATURE_ALGORITHM = SHA1WITHRSA;

    public static boolean verifyPurchase(String productId, String base64PublicKey,
                                         String signedData, String signature)
    {
        if (TextUtils.isEmpty(signedData) || TextUtils.isEmpty(base64PublicKey) ||
            TextUtils.isEmpty(signature))
        {

            if (
				productId.equals(ANDROID_TEST_PURCHASED) ||
				productId.equals(ANDROID_TEST_CANCELED) ||
				productId.equals(ANDROID_TEST_REFUNDED) ||
				productId.equals(ANDROID_TEST_ITEM_UNAVAILABLE)
                    )
            {
                return true;
            }

            Log.e(TAG, "Purchase verification failed: missing data.");
            return false;
        }

        PublicKey key = Security.generatePublicKey(base64PublicKey);
        return Security.verify(key, signedData, signature);
    }
	
    public static PublicKey generatePublicKey(String encodedPublicKey)
    {
        try
        {
            byte[] decodedKey = Base64.decode(encodedPublicKey, Base64.DEFAULT);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_FACTORY_ALGORITHM);
            return keyFactory.generatePublic(new X509EncodedKeySpec(decodedKey));
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }
        catch (InvalidKeySpecException e)
        {
            Log.e(TAG, "Invalid key specification.");
            throw new IllegalArgumentException(e);
        }
        catch (IllegalArgumentException e)
        {
            Log.e(TAG, "Base64 decoding failed.");
            throw e;
        }
    }

    public static boolean verify(PublicKey publicKey, String signedData, String signature)
    {
        Signature sig;
        try
        {
            sig = Signature.getInstance(SIGNATURE_ALGORITHM);
            sig.initVerify(publicKey);
            sig.update(signedData.getBytes());
            if (!sig.verify(Base64.decode(signature, Base64.DEFAULT)))
            {
                Log.e(TAG, "Signature verification failed.");
                return false;
            }
            return true;
        }
        catch (NoSuchAlgorithmException e)
        {
            Log.e(TAG, "NoSuchAlgorithmException.");
        }
        catch (InvalidKeyException e)
        {
            Log.e(TAG, "Invalid key specification.");
        }
        catch (SignatureException e)
        {
            Log.e(TAG, "Signature exception.");
        }
        catch (IllegalArgumentException e)
        {
            Log.e(TAG, "Base64 decoding failed.");
        }
        return false;
    }
}
