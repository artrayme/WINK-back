FROM gradle:7.3.0-jdk17-alpine

VOLUME /tmp
COPY engine engine
COPY sc-json-http-module sc-json-http-module
COPY settings.gradle settings.gradle

WORKDIR sc-json-http-module
ENTRYPOINT ["gradle","bootRun"]
EXPOSE 8080
