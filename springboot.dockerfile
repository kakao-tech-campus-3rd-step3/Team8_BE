# Build stage

FROM bellsoft/liberica-openjdk-alpine:17 AS builder

WORKDIR /app

COPY . .

RUN ./gradlew clean compileJava

RUN ./gradlew clean build -x test

VOLUME /tmp

# Run stage
FROM bellsoft/liberica-openjdk-alpine:17 AS runner

COPY --from=builder /app/build/libs/*.jar /app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]