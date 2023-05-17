# Použití oficiálního obrazu pro Java s JDK
FROM openjdk:17-jdk

# Instalace Heroku CLI
RUN apt-get update && apt-get install -y curl apt-transport-https
RUN curl https://cli-assets.heroku.com/install-ubuntu.sh | sh

# Nastavení pracovního adresáře
WORKDIR /app

# Kopírování zdrojových souborů do kontejneru
COPY . /app

# Sestavení aplikace
RUN ./mvnw clean package

# Exponování portu, na kterém běží aplikace
EXPOSE 8080

# Spuštění aplikace
CMD ["java", "-jar", "target/myapp.jar"]
