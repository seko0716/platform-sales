#!/usr/bin/env bash

docker-compose stop
docker rm $(docker ps -q)
