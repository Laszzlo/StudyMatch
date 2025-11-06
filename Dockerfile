FROM gradle:jdk25-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

LABEL org.name="Study Match"


FROM eclipse-temurin:25-jdk-alpine
COPY --from=build /home/gradle/src/build/libs/StudyMatch-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]