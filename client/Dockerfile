FROM openjdk:8-jre-alpine
ENV SERVER_HOST=localhost
COPY target/client-0.0.1-shaded.jar /client.jar
CMD /usr/bin/java -jar /client.jar $SERVER_HOST
