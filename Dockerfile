FROM eclipse-temurin:11-jre-alpine

WORKDIR /app

COPY target/transaction-service.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]