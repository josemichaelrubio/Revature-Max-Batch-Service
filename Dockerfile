FROM java:8
COPY build/libs/revature.batchgroup.1.0-SNAPSHOT.jar .
EXPOSE 8083
CMD java -jar revature.batchgroup.1.0-SNAPSHOT.jar