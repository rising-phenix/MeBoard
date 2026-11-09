@rem Gradle wrapper batch script (bootstrap).
@rem The Gradle distribution must be downloaded before this script can run.
@rem This project targets Gradle 8.0.2.

setlocal
set "APP_HOME=%~dp0.."
set "GRADLE_USER_HOME=%USERPROFILE%\.gradle"
set "WRAPPER_JAR=%GRADLE_USER_HOME%\wrapper\dists\gradle-8.0.2-bin\gradle\wrapper\gradle-wrapper.jar"

if not exist "%WRAPPER_JAR%" (
    echo Gradle wrapper not found. Please run:
    echo   gradlew wrapper --gradle-version=8.0.2
    exit /b 1
)

java -jar "%WRAPPER_JAR%" %*
