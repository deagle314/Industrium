#!/bin/sh
# Gradle wrapper script
DIRNAME="$(dirname "$0")"
APP_BASE_NAME="$(basename "$0")"
APP_HOME="$(cd "$DIRNAME" && pwd -P)"
CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar
exec /tmp/gradle/gradle-8.4/bin/gradle "$@"
