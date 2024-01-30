FROM openjdk:17-jdk-alpine
VOLUME /app
COPY target/app.jar /app/app.jar
WORKDIR /app
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-Dlog4j2.formatMsgNoLookups=true","-jar","./app.jar"]