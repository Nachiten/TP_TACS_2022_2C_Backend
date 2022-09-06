# Step 1 - Build
FROM openjdk:17-jdk-alpine as build
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN chmod +x mvnw && ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

# Step 2 - Run
FROM openjdk:17-jdk-alpine
VOLUME /tmp
ARG BUILD_FOLDER=/workspace/app/target/dependency
COPY --from=build ${BUILD_FOLDER}/BOOT-INF/lib /app/lib
COPY --from=build ${BUILD_FOLDER}/META-INF /app/META-INF
COPY --from=build ${BUILD_FOLDER}/BOOT-INF/classes /app

# Defined spring boot port by config
EXPOSE 3000

ENTRYPOINT ["java","-cp","app:app/lib/*","com.tacs.backend.BackendApplication"]