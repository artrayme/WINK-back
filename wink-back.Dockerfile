FROM gradle:7.3.0-jdk17-alpine

EXPOSE 8080
VOLUME /tmp
COPY sc-json-http-module/build/libs/sc-json-http-module-0.0.1.jar app.jar
RUN ls
ENTRYPOINT ["java","-jar","app.jar"]
