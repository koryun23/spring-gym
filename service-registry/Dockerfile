# set the base image
FROM openjdk:21-jdk

# define the name of the .jar file
ARG JAR_FILE=build/libs/service-registry.jar

# add a gymuser with uid 1234 and gymgroup with gid 1234
RUN groupadd -g 1234 gymgroup && \
    useradd -m -u 1234 -g gymgroup gymuser

# switch to the gymuser
USER gymuser

# copy the jar file from host machine to the container and rename it to app.jar
COPY ${JAR_FILE} app.jar

# run the jar file
ENTRYPOINT ["java", "-jar", "/app.jar"]
