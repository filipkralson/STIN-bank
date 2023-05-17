
FROM openjdk:17-jdk

WORKDIR /usr/src/app

COPY pom.xml .

RUN ./mvnw dependency:go-offline

COPY src ./src

RUN ./mvnw package -DskipTests

# Spuštění aplikace
CMD ["java", "-jar", "target/your-application.jar"]
