FROM openjdk:17-jdk

RUN apt-get update && \
    apt-get install -y maven

WORKDIR /usr/src/app

COPY mvnw .
COPY .mvn .mvn

COPY pom.xml .

RUN chmod +x mvnw

RUN ./mvnw dependency:go-offline

COPY src ./src

RUN ./mvnw package -DskipTests

# Spuštění aplikace
CMD ["java", "-jar", "target/your-application.jar"]
