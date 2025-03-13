FROM openjdk:17-jdk-alpine
COPY api/auth/build/libs/api-0.1.0.jar app.jar
EXPOSE 4000
ENTRYPOINT ["java","-jar","/app.jar"]