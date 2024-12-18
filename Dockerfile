FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY src/main/resources/.env /app/.env
ENTRYPOINT ["java", "-jar", "/app.jar"]
