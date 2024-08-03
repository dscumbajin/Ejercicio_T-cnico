# Usar la imagen base de Eclipse Temurin JDK 17
FROM eclipse-temurin:17

# Información del autor
LABEL author=darwin

# Copiar el archivo JAR generado por Maven al contenedor
COPY target/clientes-0.0.1-SNAPSHOT.jar app.jar

# Definir el comando de entrada
ENTRYPOINT ["java", "-jar", "app.jar"]