FROM openjdk:21
WORKDIR target/
COPY target/sensors-rest-api-1.0.0-SNAPSHOT.jar sensors-rest-api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "sensors-rest-api.jar"]