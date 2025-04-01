FROM openjdk:17-jdk-alpine
COPY build/libs/film-0.1.0.jar app.jar
EXPOSE 4000
ENTRYPOINT ["java","-Xmx64m", "-Xss256k","-jar","/app.jar"]