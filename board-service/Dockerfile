# 1단계: 빌드
FROM maven:3.9.4-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package

# 2단계: 실행
FROM openjdk:17
WORKDIR /app
COPY --from=builder /app/target/board-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
