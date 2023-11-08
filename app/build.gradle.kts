plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    //Dependiencia de SafeArgs para la tranferencia de datos entre Fragments. Otorga clases generadas automaticamente para llevar a cabo estas operaciones.
    id("androidx.navigation.safeargs.kotlin")
    //Dependencia de KSP del Lab06
    id("com.google.devtools.ksp")
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

    viewBinding {
        enable = true
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

}

dependencies {

    //NO UTILIZAR LAS DEPENDENCIAS DE GOOGLE. UTILIZAR SIEMPRE LAS DEPENDENCIAS DE ANDROIDX
    //Estas dependencias son tomadas del Lab04, y la aplicación funciona (en 2/11/2023)

    //Dependencias de Lab06 para Room
    val room_version = "2.5.0"

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    // To use Kotlin Symbol Processing (KSP)
    ksp("androidx.room:room-compiler:$room_version")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")

    // optional - Test helpers
    testImplementation("androidx.room:room-testing:$room_version")

    //Dependencias del Lab05
    implementation("com.google.code.gson:gson:2.10.1")//Para serializar y deserializar codigo Java y Kotlin
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")//Extension de Retrofit para que pueda usar Gson
    implementation("com.squareup.retrofit2:retrofit:2.9.0")//Para realizar peticiones a una API
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.11")//Interceptor HTTP. Sirve para cosas de las peticiones HTTP, pero Roberto no lo ha explicado en este video
    implementation("com.github.bumptech.glide:glide:5.0.0-rc01")//Para renderizar las imagenes, sean de la carpeta drawable o de una URL remota. Es sencillo de usar.

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