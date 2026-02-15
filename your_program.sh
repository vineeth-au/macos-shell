#!/bin/sh

set -e (
  cd "$(dirname "$0")"
  mvn -q -B package -Ddir=/tmp/codecrafters-build-shell-java
)

exec java --enable-native-access=ALL-UNNAMED --enable-preview -jar /tmp/codecrafters-build-shell-java/codecrafters-shell.jar "$@"
