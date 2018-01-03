FROM java:8-jre

EXPOSE 8082

ADD ./build/libs/communication-1.0-SNAPSHOT.jar /app/communication.jar
CMD ["java", "-Xmx200m", "-jar", "/app/communication.jar"]

