FROM java:8-jre

EXPOSE 8761

ADD ./build/libs/registration-1.0-SNAPSHOT.jar /app/registry.jar
CMD ["java", "-Xmx200m", "-jar", "/app/registry.jar"]

