FROM java:8-jre

EXPOSE 8989 8080

ADD ./build/libs/monitoring-1.0-SNAPSHOT.jar /app/monitoring.jar
CMD ["java", "-Xmx200m", "-jar", "/app/monitoring.jar"]

