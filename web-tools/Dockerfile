#
# Build stage
#
FROM maven:3.9.5-eclipse-temurin-17-alpine AS build
RUN mkdir -p /opt/app
WORKDIR /opt/app
ADD . /opt/app
RUN mvn clean package

#
# Package stage
#
FROM eclipse-temurin:17-alpine
COPY --from=build /opt/app/target/*.jar /opt/app.jar
EXPOSE 8080
CMD ["java", "-verbose:gc", "-Xmx16m", "-jar", "/opt/app.jar"]