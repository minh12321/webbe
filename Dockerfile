# --------------------------
# Stage 1: Build application
# --------------------------
    FROM maven:3.9.6-eclipse-temurin-22 AS build

    WORKDIR /app
    
    # Copy project files
    COPY .mvn/ .mvn
    COPY mvnw pom.xml ./
    COPY src ./src
    
    # Grant permission to mvnw and build the project
    RUN chmod +x mvnw && ./mvnw clean package -DskipTests
    
    # --------------------------
    # Stage 2: Run the app
    # --------------------------
    FROM eclipse-temurin:22-jdk
    
    WORKDIR /app
    
    # Copy built jar file from build stage
    COPY --from=build /app/target/*.jar app.jar
    
    EXPOSE 8080
    
    ENTRYPOINT ["java", "-jar", "app.jar"]
    