#!/usr/bin/env bash

docker rm $(docker ps -a -f status=exited -q)
docker rmi $(docker images -a -q)
#docker volume rm $(docker volume ls -q)
