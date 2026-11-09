plugins {
    id "org.jetbrains.kotlin.jvm" version "1.9.22"
    id "org.ktlint" version "10.3.0"
}

repositories {
    google()
    mavenCentral()
}

tasks.register("verifyAll") {
    dependsOn("test", ":app:assembleDebug")
}