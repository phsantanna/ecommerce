FROM maven:3.9.10 AS build

COPY src /app/src
COPY pom.xml /app

WORKDIR /app
RUN mvn clean install


FROM eclipse-temurin:17-jre-alpine

COPY --from=build /app/target/*.jar /app/app.jar

WORKDIR /app

EXPOSE 8080

CMD ["java","-jar","app.jar"]