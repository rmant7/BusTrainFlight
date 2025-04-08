buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.21")
        classpath("com.android.tools.build:gradle:8.6.0")
    }

}

plugins {
//    id("org.jetbrains.kotlin.android") version "2.0.20" apply false
//    kotlin("android") version "8.5.2" apply false
//    kotlin("plugin.serialization") version "1.6.21"
    id("com.google.devtools.ksp") version "2.0.21-1.0.25" apply false // KSP plugin
    id("com.google.dagger.hilt.android") version "2.52" apply false // Hilt plugin
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://kotlin.bintray.com/kotlinx/")
    }
}