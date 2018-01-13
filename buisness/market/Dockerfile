FROM java:8-jre

EXPOSE 8083

ADD ./build/libs/market-1.0-SNAPSHOT.jar /app/market.jar
CMD ["java", "-Xmx200m", "-jar", "/app/market.jar"]

