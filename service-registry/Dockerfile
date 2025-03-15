FROM openjdk:21-jdk
ARG JAR_FILE=build/libs/service-registry.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "/app.jar"]
