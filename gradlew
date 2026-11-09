#!/usr/bin/env sh

# Gradle wrapper shell script (bootstrap).
# The Gradle distribution must be downloaded before this script can run.
# This project targets Gradle 8.0.2.

APP_HOME="${APP_HOME:-$(cd "$(dirname "$0")/.." && pwd)}"
DISTRIBUTION_URL="https://services.gradle.org/distributions/gradle-8.0.2-bin.zip"
GRADLE_USER_HOME="${GRADLE_USER_HOME:-$HOME/.gradle}"
WRAPPER_JAR="$GRADLE_USER_HOME/wrapper/dists/gradle-8.0.2-bin/gradle/wrapper/gradle-wrapper.jar"

if [ ! -f "$WRAPPER_JAR" ]; then
    echo "Gradle wrapper not found. Please run:"
    echo "  ./gradlew wrapper --gradle-version=8.0.2"
    exit 1
fi

exec java -jar "$WRAPPER_JAR" "$@"
