plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("plugin.serialization") version "1.6.21"
}

dependencies {
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.activity:activity-ktx:1.7.2")
    implementation("androidx.recyclerview:recyclerview:1.3.1")
    implementation("com.yandex.android:mobmetricalib:5.3.0")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

    implementation("io.github.aakira:napier:2.6.1")

    implementation("io.insert-koin:koin-core:3.4.3")
    implementation("io.insert-koin:koin-android:3.4.3")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")

    // Admob
    implementation("com.google.android.gms:play-services-ads:22.3.0")

    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-runtime:2.6.1")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    annotationProcessor("androidx.lifecycle:lifecycle-compiler:2.6.1")

    implementation("com.github.satoshun.coroutine.autodispose:autodispose:0.3.1")

    implementation("androidx.coordinatorlayout:coordinatorlayout:1.2.0")

   // implementation ("com.google.android.gms:play-services-location:15.0.1")
}

android {
    compileSdkVersion(33)
    defaultConfig {
        applicationId = "ru.z8.louttsev.bustrainflightmobile.androidApp"
        minSdkVersion(21)
        targetSdkVersion(33)
        versionCode = 8
        versionName = "1.1.6"
    }
    buildTypes {
        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
        }
    }
    lintOptions {
        isCheckReleaseBuilds = false
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}