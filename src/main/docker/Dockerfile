FROM openjdk:8-jre-alpine

ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    JAVA_OPTS=""

CMD echo "The application starting.." && \
    java -jar /app.jar

ADD *.jar /app.jar