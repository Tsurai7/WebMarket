FROM openjdk:21

WORKDIR /app

COPY build/libs/WebMarket-0.0.1-SNAPSHOT.jar web-market.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "web-market.jar"]

