FROM maven:3.8.3-openjdk-17-slim as builder
WORKDIR /build
COPY ./src ./src
COPY pom.xml pom.xml
RUN mvn clean package -Dspring.profiles.active=build
FROM bellsoft/liberica-openjdk-alpine-musl
WORKDIR /app
COPY --from=builder /build/target/main-service.jar .
CMD java -jar main-service.jar