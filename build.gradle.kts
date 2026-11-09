plugins {
    kotlin("jvm") version "1.8.20"
}

repositories {
    google()
    mavenCentral()
}

tasks.register("verifyAll") {
    dependsOn("test", ":app:assembleDebug")
}