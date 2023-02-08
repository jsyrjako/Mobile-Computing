plugins {
    id("com.android.library")
    id("com.google.dagger.hilt.android")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "com.jsyrjako.core.database"
    compileSdk = sdk.compile

    defaultConfig {
        minSdk = sdk.min
        targetSdk = sdk.target

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(androidx.core.ktx)

    // Room
    implementation(androidx.room.room)
    implementation(androidx.room.runtime)
    kapt(androidx.room.compiler)

    // Coroutines
    implementation(kotlinx.coroutines.android.android)
    implementation(kotlinx.coroutines.core.core)

    // Hilt for DI
    implementation("com.google.dagger:hilt-android:2.44.2")
    kapt("com.google.dagger:hilt-android-compiler:2.44")

    testImplementation(junit.junit.junit)
    androidTestImplementation(androidx.test_ext.junit)
    androidTestImplementation(androidx.test_espresso.espresso_core)
}
kapt {
    correctErrorTypes = true
}