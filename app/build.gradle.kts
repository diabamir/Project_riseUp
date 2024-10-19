plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.project_riseup"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.project_riseup"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    dependencies {
        // AndroidX Libraries
        implementation(libs.appcompat)
        implementation(libs.material)
        implementation(libs.activity)
        implementation(libs.constraintlayout)

        implementation ("com.airbnb.android:lottie:+")
        // Room dependencies
        implementation(libs.room.common)
        implementation(libs.room.runtime)
        annotationProcessor(libs.room.compiler)

        // Firebase Crashlytics
        implementation(libs.firebase.crashlytics.buildtools)

        implementation("com.google.code.gson:gson:2.8.9")
        implementation("androidx.recyclerview:recyclerview:1.1.0")

        // Retrofit and OkHttp dependencies
        implementation ("com.squareup.retrofit2:retrofit:2.9.0")
        implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
        implementation ("com.squareup.okhttp3:logging-interceptor:4.9.0")

        // Lottie Animation dependency
        implementation ("com.airbnb.android:lottie:+")
        implementation ("com.google.android.material:material:1.8.0")  // Add this dependency
        implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
        implementation ("com.squareup.retrofit2:retrofit:2.9.0")
        implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
        implementation ("com.squareup.okhttp3:logging-interceptor:4.9.0")
        implementation ("com.squareup.retrofit2:retrofit:2.9.0")
        implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
        implementation ("com.squareup.okhttp3:logging-interceptor:4.9.0")
        implementation ("com.squareup.retrofit2:retrofit:2.9.0")
        // Testing dependencies
        testImplementation(libs.junit)
        androidTestImplementation(libs.ext.junit)
        androidTestImplementation(libs.espresso.core)
    }
    // Add ThreeTenABP for Java time support
    implementation("com.jakewharton.threetenabp:threetenabp:1.3.1")

    // Testing dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
