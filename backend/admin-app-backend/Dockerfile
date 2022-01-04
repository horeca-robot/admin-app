FROM gradle:7.3.3-jdk11-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build -x test --no-daemon 
FROM openjdk:11-jre-slim AS production
EXPOSE 8080
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]