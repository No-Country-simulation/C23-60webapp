# Imagen base ligera para Java 17
FROM eclipse-temurin:17-jdk-alpine
#argumentos variable jar_file
ARG JAR_FILE=target/agency-0.0.1.jar

# Copia el archivo JAR al contenedor
COPY ${JAR_FILE} /app/app_agency.jar

WORKDIR /app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app_agency.jar"]
