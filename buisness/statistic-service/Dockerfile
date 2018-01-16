FROM java:8-jre

EXPOSE 8084

ADD ./build/libs/statistic-service-1.0-SNAPSHOT.jar /app/statistic-service.jar
CMD ["java", "-Xmx200m", "-jar", "/app/statistic-service.jar"]

