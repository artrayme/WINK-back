FROM gradle:7.3.0-jdk17-alpine

COPY engine engine
COPY sc-json-http-module sc-json-http-module
COPY settings.gradle settings.gradle
COPY docker/build.sh build.sh
COPY kb_scripts kb_scripts
ENTRYPOINT ["sh","./build.sh"]
