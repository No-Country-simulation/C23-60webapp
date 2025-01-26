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
RUN apk add --no-cache curl && \
    curl -sSL https://github.com/jwilder/dockerize/releases/download/v0.6.1/dockerize-linux-amd64-v0.6.1.tar.gz | tar -xz -C /usr/local/bin
EXPOSE 8080
# Usa dockerize para esperar a MySQL
ENTRYPOINT ["dockerize", "-wait", "tcp://mysql:3306", "-timeout", "60s", "java", "-jar", "app.jar"]
