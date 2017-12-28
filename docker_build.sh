#!/bin/bash

docker build -t seko/platform-sales-config ./platform-sales-config
docker build -t seko/platform-sales-registry ./platform-sales-registry
docker build -t seko/platform-sales-gateway ./platform-sales-gateway
docker build -t seko/platform-sales-auth-service ./platform-sales-auth-service
docker build -t seko/platform-sales-mongodb ./platform-sales-mongodb
docker build -t seko/platform-sales-monitoring ./platform-sales-monitoring
