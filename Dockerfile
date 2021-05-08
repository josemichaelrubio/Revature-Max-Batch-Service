FROM java:8
COPY build/libs/batch-service-1.0-SNAPSHOT.jar .
EXPOSE 8083
CMD java -jar batch-service-1.0-SNAPSHOT.jar