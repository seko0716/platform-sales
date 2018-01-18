#!/usr/bin/env bash

sleep 20

CURL=curl
KIBANA_INDEX=".kibana"
ELASTICSEARCH=http://localhost:9200

DIR=kibana

for file in ${DIR}/visualization/*.json
do
    NAME=`basename ${file} .json`
    echo "Loading visualization ${NAME}:"
    ${CURL} -XPUT ${ELASTICSEARCH}/${KIBANA_INDEX}/visualization/${NAME} \
        -d @${file} || exit 1
    echo
done

for file in ${DIR}/dashboard/*.json
do
    NAME=`basename ${file} .json`
    echo "Loading dashboard ${NAME}:"
    ${CURL} -XPUT ${ELASTICSEARCH}/${KIBANA_INDEX}/dashboard/${NAME} \
        -d @${file} || exit 1
    echo
done

for file in ${DIR}/index-pattern/*.json
do
    NAME=`awk '$1 == "\"title\":" {gsub(/[",]/, "", $2); print $2}' ${file}`
    echo "Loading index pattern ${NAME}:"

    ${CURL} -XPUT ${ELASTICSEARCH}/${KIBANA_INDEX}/index-pattern/${NAME} \
        -d @${file} || exit 1
    echo
done