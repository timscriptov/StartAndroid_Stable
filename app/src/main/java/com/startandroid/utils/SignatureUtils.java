package com.startandroid.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Base64;

import org.spongycastle.cert.X509CertificateHolder;
import org.spongycastle.cms.CMSSignedData;
import org.spongycastle.util.CollectionStore;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static com.startandroid.data.Constants.SIGNATURE;

public class SignatureUtils {
    // проверяет подпись приложения
    public static boolean verifySignatureSHA(Context c) {
        return com.startandroid.utils.Utils.reverseString(directReadSignature(c)).endsWith(SIGNATURE);
    }

    // получает SHA1withRSA подпись приложения
    private static String directReadSignature(Context context) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
            ZipFile zipFile = new ZipFile(applicationInfo.publicSourceDir);
            Enumeration entries = zipFile.entries();

            ZipEntry entry;
            String name;
            do {
                do {
                    entry = (ZipEntry) entries.nextElement();
                    name = entry.getName().toUpperCase();
                }
                while (!name.startsWith("META-INF/"));
            }
            while (!name.endsWith(".RSA") && !name.endsWith(".DSA"));

            InputStream inputStream = zipFile.getInputStream(entry);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1)
                out.write(buffer, 0, bytesRead);
            byte[] content = out.toByteArray();
            inputStream.close();
            zipFile.close();

            content = ((X509CertificateHolder) ((CollectionStore) new CMSSignedData(content).getCertificates()).iterator().next()).getEncoded();

            MessageDigest messageDigest = MessageDigest.getInstance("SHA"); // "SHA"
            messageDigest.update(content);
            return Base64.encodeToString(messageDigest.digest(), Base64.DEFAULT).trim();
        } catch (Exception ignored) {
            return null;
        }
    }
}