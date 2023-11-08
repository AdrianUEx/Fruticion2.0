// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
    //Esta dependencia es de SafeArgs para pasar argumentos de un Fragment a otro (como en el caso del patron Master-Detail)
    id("androidx.navigation.safeargs.kotlin") version "2.5.3" apply false
    //Dependencia de KSP del Lab06
    id("com.google.devtools.ksp") version "1.9.10-1.0.13" apply false
}