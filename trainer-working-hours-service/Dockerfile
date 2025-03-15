FROM openjdk:21-jdk
ARG JAR_FILE=build/libs/trainer-working-hours-service-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8881
ENTRYPOINT ["java", "-jar", "/app.jar"]