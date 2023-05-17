# Použijte vhodný base image pro Java
FROM openjdk:17

# Nastavte working directory v kontejneru
WORKDIR /app

# Přidejte JAR soubor aplikace do kontejneru
COPY target/bank-0.0.1-SNAPSHOT.jar /app/app.jar

# Exponujte port, na kterém aplikace běží
EXPOSE 8080

# Spusťte aplikaci při startu kontejneru
CMD ["java", "-jar", "app.jar"]
