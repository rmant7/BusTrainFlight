import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import java.io.BufferedInputStream
import java.io.FileInputStream
import java.security.DigestInputStream
import java.security.MessageDigest

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("dev.icerock.mobile.multiplatform-resources")
    id("com.squareup.sqldelight")
    kotlin("plugin.serialization") version "1.6.21"
}

kotlin {
    android()
    ios {
        binaries {
            framework {
                baseName = "shared"
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")
                api("dev.icerock.moko:resources:0.21.1")
                api("dev.icerock.moko:mvvm-core:0.11.0")
                api("dev.icerock.moko:mvvm-livedata:0.11.0")
                implementation("com.squareup.sqldelight:runtime:1.4.4")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")

                implementation("io.github.aakira:napier:2.6.1")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.2")
                implementation("com.google.android.material:material:1.4.0")
                implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
                implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
                api("dev.icerock.moko:mvvm-livedata-material:0.11.0")
                api("dev.icerock.moko:mvvm-databinding:0.11.0")
                api("dev.icerock.moko:mvvm-viewbinding:0.11.0")
                implementation("com.squareup.sqldelight:android-driver:1.4.4")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
                implementation("androidx.test:core:1.4.0")
                implementation("com.android.support:support-annotations:28.0.0")
                implementation("com.android.support.test:runner:1.0.2")
                implementation("org.robolectric:robolectric:4.4")
            }
        }
        val iosMain by getting {
            dependencies {
                // TODO define the appropriate library dependency for coroutines, issue #25
                implementation("com.squareup.sqldelight:native-driver:1.4.4")
            }
        }
        val iosTest by getting
    }
    // export correct artifact to use all classes of library directly from Swift
    targets.withType(KotlinNativeTarget::class.java).all {
/*        val arch = when (this.konanTarget) {
            org.jetbrains.kotlin.konan.target.KonanTarget.IOS_ARM64 -> "iosarm64"
            org.jetbrains.kotlin.konan.target.KonanTarget.IOS_X64 -> "iosx64"
            else -> throw IllegalArgumentException()
        }*/
        binaries.withType(org.jetbrains.kotlin.gradle.plugin.mpp.Framework::class.java).all {
//            export("dev.icerock.moko:mvvm-$arch:0.11.0")
            export("dev.icerock.moko:mvvm-core:0.11.0")
            export("dev.icerock.moko:mvvm-livedata:0.11.0")
        }
    }
}

fun getMd5EncryptedString(file: File): String = DigestInputStream(
    BufferedInputStream(FileInputStream(file)),
    MessageDigest.getInstance("MD5")
).use {
    @Suppress("ControlFlowWithEmptyBody")
    while (it.read() != -1) { }

    it.messageDigest.digest()
        .map { String.format("%02x", it) }
        .reduce { result, string -> result + string }
}

android {
    compileSdkVersion(33)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].resources.setSrcDirs(
        listOf(
            "src/androidMain/resources",
            "src/commonMain/resources" // <-- add the commonMain Resources
        )
    )
    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(33)

        val dbFile = File("shared/src/commonMain/resources/MR/files/fullDb.sqlite3")
        val checkCode = getMd5EncryptedString(dbFile)
        buildConfigField("String", "DB_FILE_CHECK_CODE", "\"$checkCode\"")
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
    testOptions {
        unitTests {
            isReturnDefaultValues = true
            isIncludeAndroidResources = true
        }
    }
}

multiplatformResources {
    multiplatformResourcesPackage = "ru.z8.louttsev.cheaptripmobile"
}

sqldelight {
    database("LocalDb") {
        packageName = "ru.z8.louttsev.cheaptripmobile.shared.infrastructure.persistence"
        sourceFolders = listOf("sqldelightLocalDb")
    }
    database("FullDb") {
        packageName = "ru.z8.louttsev.cheaptripmobile.shared.infrastructure.datasource"
        sourceFolders = listOf("sqldelightFullDb")
    }
}

val packForXcode by tasks.creating(Sync::class) {
    group = "build"
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val sdkName = System.getenv("SDK_NAME") ?: "iphonesimulator"
    val targetName = "ios" + if (sdkName.startsWith("iphoneos")) "Arm64" else "X64"
    val framework =
        kotlin.targets.getByName<KotlinNativeTarget>(targetName).binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)
    val targetDir = File(buildDir, "xcode-frameworks")
    from({ framework.outputDirectory })
    into(targetDir)
}

tasks.getByName("build").dependsOn(packForXcode)