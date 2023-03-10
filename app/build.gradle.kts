plugins {
    id("com.android.application")
    id("com.google.dagger.hilt.android")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "com.jsyrjako.reminderapp"
    compileSdk = sdk.compile

    defaultConfig {
        applicationId = "com.jsyrjako.reminderapp"
        minSdk = sdk.min
        targetSdk = sdk.target
        versionCode =  1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":core-database"))
    implementation(project(":core-data"))
    implementation(project(":core-domain"))

    implementation(androidx.core.ktx)
    implementation(androidx.lifecycle.runtime)
    implementation(androidx.activity.activity)
    implementation(androidx.compose.ui.ui)
    implementation(androidx.compose.ui.preview)
    implementation(androidx.compose.material)
    implementation(androidx.compose.material_icons)
    testImplementation(junit.junit.junit)
    androidTestImplementation(androidx.test_ext.junit)
    androidTestImplementation(androidx.test_espresso.espresso_core)
    androidTestImplementation(androidx.compose.ui.test_junit4)
    debugImplementation(androidx.compose.ui.tooling)
    debugImplementation(androidx.compose.ui.test_manifest)

    implementation(androidx.navigation.hilt.compose)

    // Maps dependencies
    implementation("com.google.maps.android:maps-ktx:3.3.0")
    implementation("com.google.maps.android:maps-utils-ktx:3.3.0")
    implementation("com.google.android.gms:play-services-maps:18.0.0")
    implementation("com.google.maps.android:android-maps-utils:2.2.3")

    implementation("com.google.android.gms:play-services-location:18.0.0")

    // Hilt for DI
    implementation("com.google.dagger:hilt-android:2.44.2")
    kapt("com.google.dagger:hilt-android-compiler:2.44")

    // Accompanist
    implementation(google.accompanist.accompanist)

    // Navigation
    implementation(androidx.navigation.navigation)

    // Viewyoael
    implementation(androidx.lifecycle.viewmodel)

    // coroutines
    implementation(jetbrains.kotlinx.coroutines)

    // ConstraintLayout
    implementation(androidx.constraintlayout.constraintlayout)

    // Room
    implementation(androidx.room.room)
    kapt(androidx.room.compiler)

    //work
    // Kotlin + coroutines
    implementation(androidx.work.runtime_ktx)

    // optional - RxJava2 support
    implementation(androidx.work.rxjava2)

    // optional - GCMNetworkManager support
    implementation(androidx.work.gcm)

    // optional - Test helpers
    androidTestImplementation(androidx.work.testing)

    // optional - Multiprocess support
    implementation(androidx.work.multiprocess)

}
kapt {
    correctErrorTypes = true
}