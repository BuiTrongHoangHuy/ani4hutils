FROM ghcr.io/graalvm/native-image:latest as build
WORKDIR /app
COPY build/libs/auth-0.1.0.jar app.jar
RUN native-image -jar app.jar --no-fallback -o app

FROM alpine:latest
WORKDIR /app
COPY --from=build /app/app app
EXPOSE 4000
ENTRYPOINT ["/app/app"]
