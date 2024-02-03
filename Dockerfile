FROM openjdk:21 AS build
WORKDIR /app
COPY . .
RUN ./gradlew clean build

FROM openjdk:21
WORKDIR /app
COPY --from=build /app/build/libs/paymentProvider-0.0.1-SNAPSHOT.jar .
COPY docker-startup.sh .
RUN chmod a+x docker-startup.sh
EXPOSE 8080
CMD ["./docker-startup.sh"]