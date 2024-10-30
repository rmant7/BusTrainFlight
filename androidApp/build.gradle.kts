plugins {
    id("com.android.application")
    kotlin("android")
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.0-Beta2"
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

dependencies {

    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.activity:activity-ktx:1.9.3")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("com.yandex.android:mobmetricalib:5.3.0")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")

    implementation("io.github.aakira:napier:2.6.1")


    implementation("com.google.dagger:hilt-android:2.52")
    ksp("com.google.dagger:hilt-android-compiler:2.52")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.6")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.6")

    // Admob
    implementation("com.google.android.gms:play-services-ads:23.4.0")

    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-runtime:2.8.6")
    implementation("com.google.android.gms:play-services-location:21.3.0")
    //noinspection LifecycleAnnotationProcessorWithJava8
    annotationProcessor("androidx.lifecycle:lifecycle-compiler:2.8.6")

    implementation("com.github.satoshun.coroutine.autodispose:autodispose:0.3.1")

    implementation("androidx.coordinatorlayout:coordinatorlayout:1.2.0")

   // implementation ("com.google.android.gms:play-services-location:15.0.1")
}


android {
    namespace = "ru.z8.louttsev.bustrainflightmobile.androidApp"
    compileSdk = 34
    defaultConfig {
        applicationId = "ru.z8.louttsev.bustrainflightmobile.androidApp"
        minSdk = 21
        targetSdk = 34
        versionCode = 13
        versionName = "1.13"
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
    lint {
        checkReleaseBuilds = false
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

}
