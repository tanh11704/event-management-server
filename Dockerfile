FROM gradle:8.5-jdk17-alpine AS builder

WORKDIR /app

COPY gradlew .
COPY gradle/ gradle/
COPY build.gradle .
COPY settings.gradle .

RUN chmod +x ./gradlew

COPY src/ src/

RUN ./gradlew bootJar --no-daemon --exclude-task test

RUN ls -la build/libs/*.jar

FROM eclipse-temurin:17-jre-alpine

LABEL maintainer="VKU Event Management Team"
LABEL description="Event Management API Server"
LABEL version="0.0.1-SNAPSHOT"

RUN apk add --no-cache wget && \
    addgroup -S springboot && \
    adduser -S springboot -G springboot && \
    mkdir -p /app && \
    chown -R springboot:springboot /app

WORKDIR /app

COPY --from=builder --chown=springboot:springboot /app/build/libs/*.jar app.jar

USER springboot

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["java", \
    "-XX:+UseContainerSupport", \
    "-XX:MaxRAMPercentage=75.0", \
    "-XX:+ExitOnOutOfMemoryError", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE:-prod}", \
    "-jar", \
    "app.jar"]
