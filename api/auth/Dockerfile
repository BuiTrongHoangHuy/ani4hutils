FROM bellsoft/liberica-openjdk-alpine:17
COPY build/libs/auth-0.1.0.jar app.jar
EXPOSE 4000
ENTRYPOINT ["java","-Xmx32m", "-Xss256k","-XX:TieredStopAtLevel=1","-jar","/app.jar"]