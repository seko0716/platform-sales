FROM java:8-jre

EXPOSE 8888

ADD ./build/libs/configuration-1.0-SNAPSHOT.jar /app/config.jar
CMD ["java", "-Xmx200m", "-jar", "/app/config.jar"]

HEALTHCHECK --interval=10s --timeout=3s CMD curl -f http://localhost:8888/health || exit 1