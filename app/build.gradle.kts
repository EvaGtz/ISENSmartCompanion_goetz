plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
}

android {
    namespace = "fr.isen.goetz.isensmartcompanion"
    compileSdk = 35

    defaultConfig {
        applicationId = "fr.isen.goetz.isensmartcompanion"
        minSdk = 25
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    // Ajout de Material Icons Extended
    implementation("androidx.compose.material:material-icons-extended:1.5.0")
    // Ajout de la navigation Jetpack Compose
    implementation("androidx.navigation:navigation-compose:2.8.7")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation("com.google.code.gson:gson:2.10.1")
    // Retrofit and Gson dependencies
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.google.code.gson:gson:2.8.8")
    //color of the navigation bar
    implementation ("androidx.compose.material3:material3:1.0.0")
    implementation ("androidx.compose.runtime:runtime:1.5.0")
    implementation ("androidx.compose.runtime:runtime-saveable:1.5.0")
    //pour l'IA GEMINI
    implementation("com.google.ai.client.generativeai:generativeai:0.4.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    // Jetpack Navigation for Compose
    implementation ("androidx.navigation:navigation-compose:2.5.3")
    implementation ("androidx.compose.ui:ui:1.3.0")
    implementation ("androidx.compose.material3:material3:1.0.0")
    implementation ("androidx.compose.material:material-icons-extended:1.3.0")
    implementation ("androidx.compose.runtime:runtime-saveable:1.3.0")
    implementation ("androidx.navigation:navigation-compose:2.5.0")
    //BottomNavigationBar
    implementation ("androidx.compose.ui:ui:1.4.0") // or latest version
    implementation ("androidx.compose.material3:material3:1.0.0") // for Material 3 components
    implementation ("androidx.navigation:navigation-compose:2.5.0") // for Navigation
    implementation ("androidx.compose.runtime:runtime-saveable:1.4.0") // for rememberSaveable
    implementation ("androidx.compose.material3:material3:1.0.0") // Ensure you have the Material3 dependency
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0")
    implementation ("com.google.code.gson:gson:2.8.8")
    // For the Room dependency
    implementation ("androidx.room:room-runtime:2.6.1")
    implementation ("androidx.room:room-ktx:2.6.1")
    kapt ("androidx.room:room-compiler:2.6.1")
    // WorkManager for background tasks
    implementation("androidx.work:work-runtime-ktx:2.7.1")
    // Notifications
    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.compose.material3:material3:1.0.0")
    implementation("androidx.compose.ui:ui:1.4.0")
    // For SharedPreferences (you can use Android's default SharedPreferences)
    implementation("androidx.preference:preference-ktx:1.1.1")
}
