FROM ghcr.io/graalvm/native-image:latest AS build
WORKDIR /app
COPY build/libs/auth-0.1.0.jar app.jar
RUN native-image -jar app.jar --no-fallback -o app

FROM alpine:latest
COPY --from=build /app/app app
RUN apk add --no-cache libc6-compat
RUN chmod +x app
EXPOSE 4000
ENTRYPOINT ["./app"]
