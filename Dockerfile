# Use uma imagem base do OpenJDK
FROM openjdk:17-jdk-slim

# Defina o diretório de trabalho
WORKDIR /app

# Copie o JAR do projeto para o contêiner
COPY target/example-0.0.1-SNAPSHOT.jar /app/example-0.0.1-SNAPSHOT.jar

# Verificar se o JAR foi realmente gerado
RUN echo "Verificando se o JAR foi gerado..." && ls -la /app/

# Comando para executar o jar
ENTRYPOINT ["java", "-jar", "example-0.0.1-SNAPSHOT.jar"]
