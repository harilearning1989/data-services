# Use a JDK 20 base image
#FROM eclipse-temurin:20-jdk-alpine
# Use a compatible Java 20 image with ARM64 support
FROM eclipse-temurin:20-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file to the container
COPY build/libs/data-services-0.0.1-SNAPSHOT.jar data-services.jar

# Expose port 8082
EXPOSE 8083

# Run the application
ENTRYPOINT ["java", "-jar", "data-services.jar"]

#docker build -t data-services:1.0.0 .
#docker run -p 8083:8083 data-services:1.0.0

#kubectl apply -f deployment.yaml
#kubectl apply -f service.yaml
#kubectl apply -f configmap.yaml
#kubectl apply -f secrets.yaml
#kubectl get pods

# View cluster info
#kubectl cluster-info

# View all resources in the cluster
#kubectl get all --all-namespaces