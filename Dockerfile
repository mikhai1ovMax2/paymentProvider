FROM openjdk:21-jdk-slim AS build
WORKDIR /app
COPY . .
RUN ./gradlew build

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/paymentProvider-0.0.1-SNAPSHOT.jar .
COPY docker-startup.sh .
RUN chmod +x docker-startup.sh
EXPOSE 8080
CMD ["./docker-startup.sh"]