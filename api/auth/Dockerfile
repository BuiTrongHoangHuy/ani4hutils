FROM alpine:latest
COPY --from=build /app/app app
COPY api/auth/build/libs/auth-0.1.0 app
RUN chmod +x app
EXPOSE 4000
ENTRYPOINT ["./app"]
