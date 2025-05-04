FROM maven:3.8.1-openjdk-17 as build
LABEL authors="lucas"
WORKDIR /usr/src/app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /usr/src/app/target/workshop.jar /app/workshop.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "workshop.jar"]