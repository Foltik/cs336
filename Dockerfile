FROM gradle:7.2-jdk17 AS BUILD
WORKDIR /app
COPY . /app
VOLUME /home/gradle/.gradle
RUN gradle bootJar

FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=BUILD /app/build/libs/cs336-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD ["java", "-jar", "cs336-0.0.1-SNAPSHOT.jar"]
