FROM bellsoft/liberica-openjdk-alpine:17
COPY build/libs/auth-0.1.0.jar app.jar
EXPOSE 4000
ENTRYPOINT ["java","-Xmx64m", "-Xss256k","-jar","/app.jar"]