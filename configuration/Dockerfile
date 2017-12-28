FROM java:8-jre

EXPOSE 8888

ADD ./build/libs/configuration-1.0-SNAPSHOT.jar /app/config.jar
CMD ["java", "-Xmx200m", "-jar", "/app/config.jar"]

