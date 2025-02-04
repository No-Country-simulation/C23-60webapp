# Build the React frontend
FROM node:22.8.0 AS build-frontend
WORKDIR /app
COPY explora-mas/package.json ./
COPY explora-mas/package-lock.json ./
RUN npm install
COPY explora-mas ./
RUN npm run build

# Build the Spring Boot backend
FROM maven:3.8.3-openjdk-17 AS build-backend
WORKDIR /app
COPY agency_API/pom.xml .
COPY agency_API/src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy the Spring Boot jar file
COPY --from=build-backend /app/target/*.jar app.jar

# Copy the frontend build to the appropriate directory
COPY --from=build-frontend /app/build ./static
EXPOSE 8080

# Using prod properties
ENTRYPOINT ["dockerize", "java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]
