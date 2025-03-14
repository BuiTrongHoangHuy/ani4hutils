FROM alpine:latest
COPY build/native/nativeCompile/auth-0.1.0 app
RUN chmod +x app
EXPOSE 4000
ENTRYPOINT ["./app"]
