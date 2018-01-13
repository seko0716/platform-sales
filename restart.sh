#!/usr/bin/env bash

export CONFIG_SERVICE_PASSWORD=111
export MONGODB_PASSWORD=222
export COMMUNICATION_SERVICE_PASSWORD=333
export MONGO_INITDB_DATABASE=piggymetrics

./stop.sh ; ./docker_build.sh ; ./start.sh ; ./status.sh
