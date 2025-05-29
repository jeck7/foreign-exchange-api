FROM eclipse-temurin:17-jdk
VOLUME /tmp
ARG JAR_FILE=target/demo-1.0.0.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
