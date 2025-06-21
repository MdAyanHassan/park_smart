plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    alias(libs.plugins.compose.compiler)
}

secrets {
    propertiesFileName = "secrets.properties"

    defaultPropertiesFileName = "local.defaults.properties"
}

android {

    namespace = "com.example.firebasesigninwithmanualdi"
    compileSdk = 34

    defaultConfig {

        applicationId = "com.example.firebasesigninwithmanualdi"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        resourceConfigurations += listOf("en")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        multiDexEnabled = true
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
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx) {
        exclude(group = "com.google.android.gms", module = "play-services-maps")
    }
    implementation(libs.androidx.lifecycle.runtime.ktx) {
        exclude(group = "com.google.android.gms", module = "play-services-maps")
    }

    // Firebase functions
    implementation(libs.firebase.functions.ktx) {
        exclude(group = "com.google.android.gms", module = "play-services-maps")
    }
    // Stripe
    implementation(libs.stripe.android) {
        exclude(group = "com.google.android.gms", module = "play-services-maps")
    }

    // Navigation SDK Maps
    implementation(libs.navigation)

    // View model
    implementation(libs.androidx.lifecycle.viewmodel.compose) {
        exclude(group = "com.google.android.gms", module = "play-services-maps")
    }

    // Navigation
    implementation(libs.androidx.navigation.compose) {
        exclude(group = "com.google.android.gms", module = "play-services-maps")
    }

    implementation(libs.androidx.activity.compose) {
        exclude(group = "com.google.android.gms", module = "play-services-maps")
    }
    implementation(platform(libs.androidx.compose.bom)) {
        exclude(group = "com.google.android.gms", module = "play-services-maps")
    }

    implementation(libs.androidx.ui) {
        exclude(group = "com.google.android.gms", module = "play-services-maps")
    }
    implementation(libs.androidx.ui.graphics) {
        exclude(group = "com.google.android.gms", module = "play-services-maps")
    }
    implementation(libs.androidx.ui.tooling.preview) {
        exclude(group = "com.google.android.gms", module = "play-services-maps")
    }
    implementation(libs.androidx.material3) {
        exclude(group = "com.google.android.gms", module = "play-services-maps")
    }

    // Firebase Sign in with Google
    implementation(platform(libs.firebase.bom)) {
        exclude(group = "com.google.android.gms", module = "play-services-maps")
    }
    implementation(libs.firebase.auth) {
        exclude(group = "com.google.android.gms", module = "play-services-maps")
    }
    implementation(libs.play.services.auth) {
        exclude(group = "com.google.android.gms", module = "play-services-maps")
    }

    // Firebase firestore
    implementation(libs.firebase.firestore) {
        exclude(group = "com.google.android.gms", module = "play-services-maps")
    }

    // Coil library
    implementation(libs.coil.compose) {
        exclude(group = "com.google.android.gms", module = "play-services-maps")
    }

    // Retrofit
    implementation(libs.retrofit) {
        exclude(group = "com.google.android.gms", module = "play-services-maps")
    }
    implementation(libs.converter.gson) {
        exclude(group = "com.google.android.gms", module = "play-services-maps")
    }
    implementation(libs.okhttp) {
        exclude(group = "com.google.android.gms", module = "play-services-maps")
    }

    // Places sdk
    implementation(libs.places) {
        exclude(group = "com.google.android.gms", module = "play-services-maps")
    }

    // Location services
    implementation(libs.play.services.location) {
        exclude(group = "com.google.android.gms", module = "play-services-maps")
    }

    // XML Material
    implementation(libs.material) {
        exclude(group = "com.google.android.gms", module = "play-services-maps")
    }

    testImplementation(libs.junit) {
        exclude(group = "com.google.android.gms", module = "play-services-maps")
    }
    androidTestImplementation(libs.androidx.junit) {
        exclude(group = "com.google.android.gms", module = "play-services-maps")
    }
    androidTestImplementation(libs.androidx.espresso.core) {
        exclude(group = "com.google.android.gms", module = "play-services-maps")
    }
    androidTestImplementation(platform(libs.androidx.compose.bom)) {
        exclude(group = "com.google.android.gms", module = "play-services-maps")
    }
    androidTestImplementation(libs.androidx.ui.test.junit4) {
        exclude(group = "com.google.android.gms", module = "play-services-maps")
    }
    debugImplementation(libs.androidx.ui.tooling) {
        exclude(group = "com.google.android.gms", module = "play-services-maps")
    }
    debugImplementation(libs.androidx.ui.test.manifest) {
        exclude(group = "com.google.android.gms", module = "play-services-maps")
    }

    // Desugaring
    coreLibraryDesugaring(libs.desugar.jdk.libs.nio) {
        exclude(group = "com.google.android.gms", module = "play-services-maps")
    }
}