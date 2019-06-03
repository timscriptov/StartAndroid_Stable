#include <jni.h>
#include <string>
#include <android/log.h>
#include <string.h>
 
extern "C"
jint IS_DEBUG = false;
const char* rsa = "caa27274fbc890bf230e577713db40e60c6d15f88d66eedbc159d702a2c46cdbd8228a3d77395aecaaa4bdf6c59cf254f75ebe56e0ad71b768fbcd6d5d45e7c1d225e66196aab52d1c6798c8227556b2b0d54e74eae1fa5407533006f7ea888312aa7b6b75c6195ef11e88219bdcb717084e7d093805a07d8e3212f48a6667b38c86ab1387a3d0da5cf1686527505b561d5bbd5ba7a8ef8f216507209de1cc0ad5a56a2a4433b4171907b02dd29766686b872e6ef1cc154e24a8d7f726b975180e05c76a90029a88eb4381028a1a033e65426d37bb1276a1317f56f4330101d0bc3b1778a91a5d83204bf33e4d633e3894776bde7d64970d95094a928e8636fb";
jint ERROR = -1, ACCEPTED = 1, NOT_ACCEPTED = 0;
jint verifyCertificate(JNIEnv *env, jobject obj, jobject cnt) {
    jclass cls = env->GetObjectClass(cnt);
    jmethodID mid = env->GetMethodID(cls, "getPackageManager",
                                     "()Landroid/content/pm/PackageManager;");
    jmethodID pnid = env->GetMethodID(cls, "getPackageName",
                                      "()Ljava/lang/String;");
    if (mid == 0 || pnid == 0) {
        return ERROR;
    }
 
    jobject pacMan_o = env->CallObjectMethod(cnt, mid);
    jclass pacMan = env->GetObjectClass(pacMan_o);
    jstring packName = (jstring) env->CallObjectMethod(cnt, pnid);
 
    /*flags = PackageManager.GET_SIGNATURES*/
    int flags = 0x40;
    mid = env->GetMethodID(pacMan, "getPackageInfo",
                           "(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;");
    if (mid == 0) {
        return ERROR;
    }
    jobject pack_inf_o = (jobject) env->CallObjectMethod(pacMan_o, mid,
                                                         packName, flags);
 
    jclass packinf = env->GetObjectClass(pack_inf_o);
    jfieldID fid;
    fid = env->GetFieldID(packinf, "signatures",
                          "[Landroid/content/pm/Signature;");
    jobjectArray signatures = (jobjectArray) env->GetObjectField(pack_inf_o,
                                                                 fid);
    jobject signature0 = env->GetObjectArrayElement(signatures, 0);
    mid = env->GetMethodID(env->GetObjectClass(signature0), "toByteArray",
                           "()[B");
    jbyteArray cert = (jbyteArray) env->CallObjectMethod(signature0, mid);
    if (cert == 0) {
        return ERROR;
    }
    jclass BAIS = env->FindClass("java/io/ByteArrayInputStream");
    if (BAIS == 0) {
        return ERROR;
    }
    mid = env->GetMethodID(BAIS, "<init>", "([B)V");
    if (mid == 0) {
        return ERROR;
    }
    jobject input = env->NewObject(BAIS, mid, cert);
 
    jclass CF = env->FindClass("java/security/cert/CertificateFactory");
    mid = env->GetStaticMethodID(CF, "getInstance",
                                 "(Ljava/lang/String;)Ljava/security/cert/CertificateFactory;");
 
    jstring X509 = env->NewStringUTF("X509");
    jobject cf = env->CallStaticObjectMethod(CF, mid, X509);
    if (cf == 0) {
        return ERROR;
    }
    //"java/security/cert/X509Certificate"
    mid = env->GetMethodID(CF, "generateCertificate",
                           "(Ljava/io/InputStream;)Ljava/security/cert/Certificate;");
    if (mid == 0) {
        return ERROR;
    }
    jobject c = env->CallObjectMethod(cf, mid, input);
    if (c == 0) {
        return ERROR;
    }
    jclass X509Cert = env->FindClass("java/security/cert/X509Certificate");
    mid = env->GetMethodID(X509Cert, "getPublicKey",
                           "()Ljava/security/PublicKey;");
    jobject pk = env->CallObjectMethod(c, mid);
    if (pk == 0) {
        return ERROR;
    }
    mid = env->GetMethodID(env->GetObjectClass(pk), "toString",
                           "()Ljava/lang/String;");
    if (mid == 0) {
        return ERROR;
    }
    jstring all = (jstring) env->CallObjectMethod(pk, mid);
    const char * all_char = env->GetStringUTFChars(all, NULL);
    char * out = NULL;
    if (all_char != NULL) {
        char * startString = (char*)strstr((const char *)all_char, (const char *)"modulus:");
        char * end = (char*)strstr((const char *)all_char, (const char *)"public exponent");
        bool isJB = false;
        if (startString == NULL) {
            //4.1.x
            startString = (char*)strstr((const char *)all_char, (const char *)"modulus=");
            end = (char*)strstr((const char *)all_char, (const char *)",publicExponent");
            isJB = true;
        }
        if (startString != NULL && end != NULL) {
            int len;
            if (isJB) {
                startString += strlen("modulus=");
                len = end - startString;
            } else {
                startString += strlen("modulus:");
                len = end - startString - 5; /* -5 for new lines*/
            }
            out = new char[len + 2];
            strncpy(out, startString, len);
            out[len] = '\0';
        }
    }
 
    env->ReleaseStringUTFChars(all, all_char);
    char * is_found = strstr(out, rsa);
 
    // при отладке сертификат не проверяем
    //__android_log_print(ANDROID_LOG_DEBUG, "RSA_kek", "OUT: %s", out);
    if (IS_DEBUG) return 1;
    else if (is_found != NULL) return 1;
    else return 0;
}
 
extern "C" {
JNIEXPORT void JNICALL Java_com_startandroid_utils_SignatureCheck_init(JNIEnv *env, jobject obj, jobject ctx);
JNIEXPORT jint JNICALL Java_com_startandroid_utils_SignatureCheck_getSomeValue(JNIEnv *env, jobject obj);
};
 
jint isCertCorrect = 0;
JNIEXPORT void JNICALL Java_com_startandroid_utils_SignatureCheck_init(JNIEnv *env, jobject obj, jobject ctx) {
    isCertCorrect = verifyCertificate(env, obj, ctx);
}
 
JNIEXPORT jint JNICALL Java_com_startandroid_utils_SignatureCheck_getSomeValue(JNIEnv *env, jobject obj) {
    if (isCertCorrect) {
        return ACCEPTED;
    } else {
        return NOT_ACCEPTED;
    }
}
 
