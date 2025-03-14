FROM openjdk:23-jdk-slim
WORKDIR /app
COPY . .
RUN mvn clean package
COPY target/swift-api-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
