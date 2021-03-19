package com.startandroid.data

import com.startandroid.BuildConfig

object Constants {
    @JvmField
    val PREMIUM = if (BuildConfig.DEBUG) "android.test.purchased" else "premium"
    const val SIGNATURE = "w8/9qeZvyqDJ7hL4gYmpaL+ftAk"
    const val SIGNATURE_2 = "w8/9qeZvyqDJ7hL4gYmpaL+ftAk"
    const val LK = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjwVwPnZ+iwu4+dAUMoO+SOY4XKsLIzsE9hCgxcgjZdKY0poUgqcadOAebfW+LkZfced9VaRodxQIXVZcdYruFW9mDMW69/dqnvjY7adYMMBpEh58E6Mq2Yt4AZYbOaquW+I6DmtvlHa34Vh3eyj3RDiaY3bS2yhpeCd5Rdwcnhjk7mE15gY+wM9bSe9AGmqx+FKlU6xww8drqWYR/xnov5seF1NuIQTzWoe0muKB+lnqhOeN9q8cOawoZqlSRagqYe9zrQ0SrfJ7N3e5nT2LpgQ33IC+Tvzkz3TwfFPbVGheuTlcWV0PweXwvaMkf2dYRcfiF4zFhIZnzKmm46aELwIDAQAB"
    const val mobileAdsId = "ca-app-pub-1411495427741055~3216954118"

    @JvmField
    var rewardedId = if (BuildConfig.DEBUG) "ca-app-pub-3940256099942544/5224354917" else "ca-app-pub-1411495427741055/3838818872"
    @JvmField
    var bannerId = if (BuildConfig.DEBUG) "ca-app-pub-3940256099942544/6300978111" else "ca-app-pub-1411495427741055/5959092554"
    @JvmField
    var intertialId = if (BuildConfig.DEBUG) "ca-app-pub-3940256099942544/1033173712" else "ca-app-pub-1411495427741055/3241004842"
    @JvmStatic
    val resPath: String
        get() = if (Preferences.offline) {
            "data/data/com.startandroid/files/resources/pages"
        } else {
            "https://timscriptov.github.io/StartAndroid/pages"
        }
}