FROM gradle:7.3.0-jdk17-alpine

VOLUME /tmp
COPY sc-json-http-module/build/libs/sc-json-http-module-0.0.1.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
EXPOSE 8080
