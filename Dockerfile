# Build stage
FROM gradle:8.11-jdk21-alpine AS build
WORKDIR /app

# Copiar arquivos de configuração do Gradle
COPY build.gradle settings.gradle ./
COPY gradle ./gradle

# Copiar código fonte
COPY src ./src

# Compilar a aplicação
RUN gradle build -x test --no-daemon

# Runtime stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Criar usuário não-root para segurança
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copiar o JAR compilado
COPY --from=build /app/build/libs/*.jar app.jar

# Expor porta da aplicação
EXPOSE 8080

# Configurações de JVM otimizadas para container
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:+UseG1GC"

# Executar a aplicação
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
