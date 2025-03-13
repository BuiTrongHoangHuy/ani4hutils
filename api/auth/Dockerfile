FROM openjdk:17-jdk-alpine
COPY auth/build/libs/auth-0.1.0.jar app.jar
EXPOSE 4000
ENTRYPOINT ["java","-jar","/app.jar"]