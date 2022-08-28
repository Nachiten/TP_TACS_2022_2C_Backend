#! /bin/bash
# $1 is the token of the project of sonar

mvn clean verify sonar:sonar \
  -Dsonar.projectKey=TP_TACS_2022_2C_Backend \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=$1