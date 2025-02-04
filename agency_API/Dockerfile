##Construir front
FROM node:22.8.0 AS build-frontend
WORKDIR /app
COPY ../explora-mas/package.json ./
RUN npm install
COPY ../explora-mas ./
RUN npm run build

#Etapa de construccion / build
#Imagen de maven con jdk17 para compilar
FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app
# Se copia el pom y directorio src
COPY pom.xml .
COPY src ./src
#Creo archivo .jar
RUN mvn clean package -DskipTests

#Etapa Runtime
#Imagen de JRE
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
#Se copia el .jar que se contruyo anres
COPY --from=build /app/target/*.jar app.jar
# Instalar dockerize, herramienta para hacer que la app espere a que mysql este OK
COPY --from=build-frontend /app/build ./static
RUN apk add --no-cache curl && \
    curl -sSL https://github.com/jwilder/dockerize/releases/download/v0.6.1/dockerize-linux-amd64-v0.6.1.tar.gz | tar -xz -C /usr/local/bin
EXPOSE 8080
# Usa dockerize para esperar a MySQL
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]
#Haciendo un cambio para que me lo tome