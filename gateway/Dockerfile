FROM java:8-jre

ADD ./build/libs/gateway-1.0-SNAPSHOT.jar /app/gateway.jar
CMD ["java", "-Xmx200m", "-jar", "/app/gateway.jar"]

EXPOSE 4000