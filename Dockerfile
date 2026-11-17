
FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

COPY src/ ./src/

RUN mkdir -p out && javac -encoding UTF-8 -d out src/Main.java

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

RUN addgroup -S app && adduser -S app -G app

COPY --from=build /app/out ./out

USER app

ENV PORT=8080
EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD wget -qO- "http://localhost:${PORT}/health" || exit 1

CMD ["java", "-cp", "out", "Main"]
