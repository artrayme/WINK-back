#!/bin/bash
docker build --no-cache -f ./wink-back-builder.Dockerfile -t artrayme/wink-back .
docker run -v $(pwd)/docker:/docker -t artrayme/wink-back -it
cd docker
docker-compose build --no-cache wink-back
docker-compose up