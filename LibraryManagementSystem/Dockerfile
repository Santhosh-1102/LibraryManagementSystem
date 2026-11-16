# Multi-stage Dockerfile for Spring Boot 3 + Java 21
# Stage 1: Build JAR using Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom.xml and source code
COPY pom.xml .
COPY src ./src

# Build production JAR without running tests
RUN mvn clean package -DskipTests

# Stage 2: Runtime image using lightweight JRE
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Run as non-root user for cloud security best practices
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy compiled JAR from build stage
COPY --from=build /app/target/LibraryManagementSystem-*.jar app.jar

# Cloud providers dynamically inject PORT (e.g. 8085 or 10000 or 80)
EXPOSE 8085

# Optimal container JVM settings
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dserver.port=${PORT:8085}", "-jar", "app.jar"]
