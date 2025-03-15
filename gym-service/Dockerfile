FROM openjdk:21-jdk
ARG JAR_FILE=build/libs/gym-service-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "/app.jar"]