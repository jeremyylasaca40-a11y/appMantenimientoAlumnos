plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.appmantenimientoalumnos"
    compileSdk = 35 // <- Cambiado de 34 a 35

    defaultConfig {
        applicationId = "com.example.appmantenimientoalumnos"
        minSdk = 28
        targetSdk = 34 // Mantén targetSdk en 34
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
    // Forzar versión compatible con SDK 34/35
    implementation("androidx.core:core-ktx:1.13.1")

    implementation("androidx.appcompat:appcompat:1.7.0") // Bajar a 1.7.0 si persiste el aviso de Gradle
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}