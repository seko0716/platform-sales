#!/bin/bash

docker build -t seko/platform-sales-config ./configuration/
docker build -t seko/platform-sales-registry ./registration/.
docker build -t seko/platform-sales-gateway ./gateway/.
docker build -t seko/platform-sales-auth-service ./auth-service/.
docker build -t seko/platform-sales-mongodb ./mongodb/.
docker build -t seko/platform-sales-monitoring ./monitoring/.
