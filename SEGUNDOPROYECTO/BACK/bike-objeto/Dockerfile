FROM openjdk:8-jdk-alpine

# Add Maintainer Info
LABEL maintainer="janerodr@espol.edu.ec"

# Add a volume pointing to /tmp
VOLUME /lp-bike

# Add the application's jar to the container
ADD /target/*.jar app.jar
# Run the jar file
#RUN apk add xfce4
 
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-DSERVER_PORT=${SERVER_PORT}","-DPROFILE:${PROFILE}","-jar","/app.jar"]

