// Data storage module (Android Room/SQLite; Android-only)

plugins {
    id("com.android.library") version "8.2.0"
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "dev.meboard.data"
    compileSdk = 36
}

dependencies {
    implementation(project(":engine:api"))
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.core:core-ktx:1.12.0")
}