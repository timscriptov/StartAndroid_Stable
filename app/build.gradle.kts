plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.startandroid"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.startandroid"
        minSdk = 24
        targetSdk = 33
        versionCode = 1196
        versionName = "1.19.6"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":melissa"))
    implementation(project(":multiline-collapsing-toolbar"))

    // Android libs
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.cardview)
    implementation(libs.androidx.vectordrawable)
    implementation(libs.androidx.preference.ktx)
    implementation(libs.androidx.webkit)
    implementation(libs.androidx.core.ktx)
    // required to avoid crash on Android 12 API 31
    implementation(libs.androidx.work.runtime.ktx)

    implementation(libs.billing)

    // Google libs
    implementation(libs.material)
    //implementation 'com.google.android.gms:play-services:12.0.1'
    //implementation 'com.google.android.play:core-ktx:1.8.1'

    implementation(libs.library)
    implementation(libs.pkix)

    implementation(libs.zt.zip)
    implementation(libs.xor)

    implementation(libs.annotations)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
}