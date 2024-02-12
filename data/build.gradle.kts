import java.io.FileInputStream
import java.util.Properties


plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

val credentialDataProperties = credentialData()

fun credentialData(): Properties {
    val secretPropertiesFile = rootProject.file("secrets.properties")
    val secretProperties = Properties()
    if (secretPropertiesFile.exists()) {
        secretProperties.load(FileInputStream(secretPropertiesFile))
    }
    return secretProperties
}

val baseURL by extra { credentialDataProperties.getProperty("BASE_URL") }
val authToken by extra { credentialDataProperties.getProperty("AUTH_TOKEN") }

android {
    namespace = "com.aungbophyoe.data"
    compileSdk =  Sdk.compile

    defaultConfig {
        minSdk = Sdk.min
        targetSdk = Sdk.target

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "BASE_URL", "\"$baseURL\"")
        buildConfigField("String", "AUTH_TOKEN", "\"$authToken\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = Config.sourceCompatibility
        targetCompatibility = Config.targetCompatibility
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        buildConfig = true
    }
}

kapt {
    correctErrorTypes = true
}


dependencies {

    implementation(Lib.core_ktx)
    implementation(Lib.app_compat)
    implementation("com.google.android.material:material:1.11.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation(Lib.dagger_hilt_android)
    kapt(Lib.kapt_hilt_android_compiler)

    implementation(Lib.retrofit)
    implementation(Lib.retrofit_gson_converter)
    implementation(Lib.moshi_converter)
    implementation(Lib.moshi)
}