#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM openjdk:13-jdk-alpine
COPY --from=build /home/app/target/*.jar backend-service.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","backend-service.jar"]