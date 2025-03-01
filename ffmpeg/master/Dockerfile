FROM alpine:3

RUN apk update && apk add git

RUN apk add --no-cache ffmpeg

WORKDIR /app

COPY app .



# Run the binary
CMD ["./app"]