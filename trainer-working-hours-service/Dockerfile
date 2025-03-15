FROM openjdk:21-jdk

ARG JAR_FILE=build/libs/trainer-working-hours-service-0.0.1-SNAPSHOT.jar

RUN groupadd -g 1234 gymgroup && \
    useradd -m -u 1234 -g gymgroup gymuser

USER gymuser

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
