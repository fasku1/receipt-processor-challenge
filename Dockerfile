FROM gradle:8.0.2-jdk17 AS builder

WORKDIR /app

# Copy Gradle wrapper scripts and folder
COPY gradlew .
COPY gradle gradle

# Copy build scripts
COPY build.gradle settings.gradle ./

# Copy source code
COPY src ./src

# Build the jar inside the container using Gradle wrapper
RUN ./gradlew build --no-daemon

FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the jar from builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
