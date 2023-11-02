plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    //Dependiencia de SafeArgs para la tranferencia de datos entre Fragments. Otorga clases generadas automaticamente para llevar a cabo estas operaciones.
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.fruticion"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.fruticion"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
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
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    viewBinding {
        enable = true
    }
}

dependencies {

    //NO UTILIZAR LAS DEPENDENCIAS DE GOOGLE. UTILIZAR SIEMPRE LAS DEPENDENCIAS DE ANDROIDX
    //Estas dependencias son tomadas del Lab04, y la aplicación funciona (en 2/11/2023)

    //Las Preference
    implementation("androidx.preference:preference-ktx:1.2.1")

    // Navigation Component
    val navVersion = "2.5.3" //La version 2.5.3 es la ultima version estable de NavigationComponent (de 22/02/2023)

    // Kotlin
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")

    // Feature module Support
    implementation("androidx.navigation:navigation-dynamic-features-fragment:$navVersion")

    // Testing Navigation (testeo del NavigationComponent)
    androidTestImplementation("androidx.navigation:navigation-testing:$navVersion")

    // Jetpack Preferences
    implementation("androidx.preference:preference-ktx:1.2.0")

    // Jetpack Compose Integration
    implementation("androidx.navigation:navigation-compose:$navVersion")

    implementation("androidx.core:core-ktx:1.9.0")//Esta libreria no puede faltar (SharedPreferences y otras cosas)
    implementation("androidx.appcompat:appcompat:1.6.1")//Esta libreria es para proporcionar compatibilidad con las versiones anteriores de Android para la IU
    implementation("com.google.android.material:material:1.9.0")//Elementos como botones, barras de herramientas y tal basados en los principios de la biblioteca Material Design de Google
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

}