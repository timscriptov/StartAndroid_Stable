package com.startandroid.data;

import com.startandroid.BuildConfig;
import com.startandroid.utils.Utils;

import org.jetbrains.annotations.NotNull;

import static com.startandroid.data.Preferences.isOffline;

public final class Constants {
    public static final String PACKAGE_NAME = Utils.fromBase64("Y29tLnN0YXJ0YW5kcm9pZA==");//com.startandroid
    public static final String UPDATE_PATH = Utils.fromBase64("aHR0cHM6Ly9tY2FsLWxsYy5naXRodWIuaW8vc2EvY29uZmlnL3VwZGF0ZS54bWw=");//https://mcal-llc.github.io/sa/config/update.xml
    public static final String LK = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjwVwPnZ+iwu4+dAUMoO+SOY4XKsLIzsE9hCgxcgjZdKY0poUgqcadOAebfW+LkZfced9VaRodxQIXVZcdYruFW9mDMW69/dqnvjY7adYMMBpEh58E6Mq2Yt4AZYbOaquW+I6DmtvlHa34Vh3eyj3RDiaY3bS2yhpeCd5Rdwcnhjk7mE15gY+wM9bSe9AGmqx+FKlU6xww8drqWYR/xnov5seF1NuIQTzWoe0muKB+lnqhOeN9q8cOawoZqlSRagqYe9zrQ0SrfJ7N3e5nT2LpgQ33IC+Tvzkz3TwfFPbVGheuTlcWV0PweXwvaMkf2dYRcfiF4zFhIZnzKmm46aELwIDAQAB";
    public static final String MI = "00384380151574298327";
    public static final String PREMIUM = BuildConfig.DEBUG ? "android.test.purchased" : Utils.fromBase64("cHJlbWl1bQ=="); // premium
	public static final String IS_PREMIUM = Utils.fromBase64("aXNQcmVtaXVt"); // isPremium
    public static final String ANTIPATCH = Utils.fromBase64("Y2MuYmlubXQuc2lnbmF0dXJlLlBtc0hvb2tBcHBsaWNhdGlvbg=="); // cc.binmt.signature.PmsHookApplication
    public static final String ANTIPATCH1 = Utils.fromBase64("YW55bXkuc2lnbi5CaW5TaWduYXR1cmVGaXg="); // anymy.sign.BinSignatureFix
    public static final String ANTIPATCH2 = Utils.fromBase64("YXBrZWRpdG9yLnBhdGNoLnNpZ25hdHVyZS5GaXg="); // apkeditor.patch.signature.Fix
    public static final String ANTIPATCH3 = Utils.fromBase64("Y29tLmFueW15LnJlZmxlY3Rpb24="); // com.anymy.reflection
    public static final String INITIALIZE = Utils.fromBase64("Y2EtYXBwLXB1Yi0xNDExNDk1NDI3NzQxMDU1fjMyMTY5NTQxMTg="); // ca-app-pub-1411495427741055~3216954118
    public static final String ADMOB_BANNER = Utils.fromBase64("Y2EtYXBwLXB1Yi0xNDExNDk1NDI3NzQxMDU1LzU5NTkwOTI1NTQ="); // ca-app-pub-1411495427741055/5959092554
    public static final String ADMOB_INTERSTITIAL = Utils.fromBase64("Y2EtYXBwLXB1Yi0xNDExNDk1NDI3NzQxMDU1LzMyNDEwMDQ4NDI="); // ca-app-pub-1411495427741055/3241004842
    public static final String META_INF = Utils.fromBase64("TUVUQS1JTkYv"); // META-INF/
    public static final String RSA = Utils.fromBase64("LlJTQQ=="); // .RSA
    public static final String DSA = Utils.fromBase64("LkRTQQ=="); // .DSA
    public static final String SHA = Utils.fromBase64("U0hB"); // SHA
	public static final String SIGNATURE = Utils.fromBase64("dzgvOXFlWnZ5cURKN2hMNGdZbXBhTCtmdEFr"); // w8/9qeZvyqDJ7hL4gYmpaL+ftAk
    public static final String STARTANDROID_ZIP = Utils.fromBase64("aHR0cHM6Ly90aW1zY3JpcHRvdi5naXRodWIuaW8vbGVzc29ucy9zdGFydGFuZHJvaWQuemlw"); // https://timscriptov.github.io/lessons/startandroid.zip
	public static final String MCAL_LLC = Utils.fromBase64("aHR0cHM6Ly9tY2FsLWxsYy5naXRodWIuaW8vc2EvcGFnZXM="); // https://mcal-llc.github.io/sa/pages
	@NotNull
    public static String getResPath() {
        if (isOffline()) return "data/data/" + PACKAGE_NAME + "/files/resources/pages";
        else return MCAL_LLC;
    }
}
