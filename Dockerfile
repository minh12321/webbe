# --- Stage 1: Build ---
    FROM maven:3.9.5-eclipse-temurin-17 AS build

    WORKDIR /app
    
    # Copy project files
    COPY . .
    
    # Build project, skip tests
    RUN mvn clean package -DskipTests
    
    # --- Stage 2: Run ---
    FROM eclipse-temurin:17-jdk
    
    WORKDIR /app
    
    # Copy jar from previous build
    COPY --from=build /app/target/*.jar app.jar
    
    # Expose port (đổi nếu bạn dùng port khác)
    EXPOSE 8080
    
    # Run the app
    ENTRYPOINT ["java", "-jar", "app.jar"]
    