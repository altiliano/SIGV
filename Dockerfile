FROM openjdk:8-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} sigv-app.jar
ENTRYPOINT ["java","-jar","/sigv-app.jar"]