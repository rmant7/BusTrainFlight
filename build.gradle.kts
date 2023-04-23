buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("dev.icerock:mobile-multiplatform:0.12.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.30")
        classpath("com.android.tools.build:gradle:4.2.2")
        classpath("dev.icerock.moko:resources-generator:0.17.0")
        classpath("com.squareup.sqldelight:gradle-plugin:1.4.4")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://kotlin.bintray.com/kotlinx/")
    }
}