FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY target/email-service-1.0.0.jar app.jar

EXPOSE 8085

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
