FROM eclipse-temurin:21-jre

# Copy the built jar file into the image
COPY target/RateLimit-0.0.1-SNAPSHOT.jar app.jar

# Run the jar file
ENTRYPOINT ["java", "-jar", "/app.jar"]
