FROM openjdk:17-alpine
WORKDIR /app
COPY build/libs/SecurityJwtAuth20-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]