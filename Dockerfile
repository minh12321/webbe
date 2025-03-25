FROM maven:3.9.9-eclipse-temurin-17-alpine AS build
COPY . .
RUN mvn clean package -Dskiptests

FROM eclipse-temurin:17-alpine-3.21 
COPY --from=build /target/*.jar demo.jar
EXPOSE : 8080
ENTRYPOINT ["java","-jar","demo.jar"]