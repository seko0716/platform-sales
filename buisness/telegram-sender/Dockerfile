FROM java:8-jre

EXPOSE 8081

ADD ./build/libs/telegram-sender-1.0-SNAPSHOT.jar /app/telegramsender.jar
CMD ["java", "-Xmx200m", "-jar", "/app/telegramsender.jar"]

