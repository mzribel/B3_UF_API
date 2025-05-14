FROM maven:3.9.6-eclipse-temurin-21 AS build
LABEL authors="mzribel"

WORKDIR /app

# On copie tous les fichiers utiles
COPY .mvn/ .mvn/
COPY mvnw ./mvnw
RUN ls -l ./mvnw && cat ./mvnw | head -n 5
COPY pom.xml .

# Important : rendre le script exécutable (Docker Linux ne garde pas les droits Windows)
RUN chmod +x ./mvnw

# Téléchargement des dépendances pour cache Maven
RUN ./mvnw dependency:go-offline

# Copie du code source
COPY src/ src/

# Compilation + tests
RUN ./mvnw clean verify -DskipTests=false

# Étape 2 : image minimale avec juste le JAR
FROM eclipse-temurin:21-jre

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]