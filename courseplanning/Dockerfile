FROM openjdk:22-jdk-slim

# Install procps package for the dev container
RUN apt-get update && apt-get install -y procps

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven wrapper and pom.xml
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Set execute permissions for the Maven wrapper
RUN chmod +x ./mvnw

# Install project dependencies without running tests
RUN ./mvnw dependency:resolve

# Copy the source code
COPY src ./src

# Run the application
CMD ["./mvnw", "spring-boot:run"]
