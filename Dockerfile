FROM maven:3-openjdk-17 as build

WORKDIR /usr/app

COPY pom.xml .
RUN mvn dependency:go-offline

ADD src src/
RUN mvn package -Dmaven.test.skip

FROM openjdk:17-jdk-alpine
COPY --from=build /usr/app/target/backend-5.0.jar /app/runner.jar
RUN apk add --update --no-cache tzdata
ENV TZ=America/Buenos_Aires
# Defined spring boot port by config
EXPOSE 3000

ENTRYPOINT ["java", "-jar", "/app/runner.jar"]