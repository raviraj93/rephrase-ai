# Step 1: Build with Gradle and JDK 21
FROM gradle:8.5-jdk21 AS builder
WORKDIR /app
COPY --chown=gradle:gradle . .
RUN gradle build -x test

# Step 2: Run with lightweight JDK 21 runtime
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

# Set env vars (override during deployment)
ENV OPENAI_API_KEY=your-default-key
ENV OPENAI_API_URL=https://api.openai.com/v1/chat/completions

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
