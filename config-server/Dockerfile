FROM maven:3.8.3-openjdk-17-slim as builder
WORKDIR /build
ARG GIT_USERNAME
ARG GIT_PASSWORD

ENV GIT_USERNAME=${GIT_USERNAME}
ENV GIT_PASSWORD=${GIT_PASSWORD}

COPY ./src ./src
COPY pom.xml pom.xml
RUN mvn clean package -Dspring.profiles.active=build -DskipTests
FROM bellsoft/liberica-openjdk-alpine-musl
WORKDIR /app
COPY --from=builder /build/target/config-server.jar .
CMD java -jar config-server.jar