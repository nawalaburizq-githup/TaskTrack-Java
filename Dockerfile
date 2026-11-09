
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY src/ ./src/

RUN mkdir -p out && javac -encoding UTF-8 -d out src/Main.java

EXPOSE 8080

CMD ["java", "-cp", "out", "Main"]

