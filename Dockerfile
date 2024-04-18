FROM openjdk:latest
COPY target/GestioneCucina-0.0.1-SNAPSHOT.jar GestioneCucina-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","GestioneCucina-0.0.1-SNAPSHOT.jar"]