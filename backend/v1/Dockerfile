# Use maven for building
FROM openjdk:21-jdk-slim AS build

# Install Maven
RUN apt-get update && apt-get install -y maven

# Set the working directory inside the container
WORKDIR /backend

# Copy pom.xml and source code
COPY . .

# Build the application with maven
RUN mvn clean package -DskipTests

# create the runtime image
FROM openjdk:21-jdk-slim

# Copy the built JAR file
COPY --from=build /backend/target/backend.v1.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Define the command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]