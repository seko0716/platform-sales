FROM java:8-jre

EXPOSE 5000

ADD ./build/libs/auth-service-1.0-SNAPSHOT.jar /app/auth-service.jar
CMD ["java", "-Xmx200m", "-jar", "/app/auth-service.jar"]