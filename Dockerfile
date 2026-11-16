# Multi-stage Dockerfile for Spring Boot 3 + Java 21 (from repo root)
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

COPY LibraryManagementSystem/pom.xml ./pom.xml
COPY LibraryManagementSystem/src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=build /app/target/LibraryManagementSystem-*.jar app.jar

EXPOSE 8085

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dserver.port=${PORT:8085}", "-jar", "app.jar"]
