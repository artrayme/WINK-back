#!/bin/bash
gradle bootJar
for f in sc-json-http-module/build/libs/*jar; do
  cp "$f" /docker/application.jar
  break
done
