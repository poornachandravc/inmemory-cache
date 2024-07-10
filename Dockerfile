FROM openjdk:17
WORKDIR /app
COPY ./target/manager-0.0.1-SNAPSHOT.jar /app
EXPOSE 8080
CMD ["java", "-jar", "manager-0.0.1-SNAPSHOT.jar"]