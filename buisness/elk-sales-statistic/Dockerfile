FROM sebp/elk

ADD /logstash/app_log.conf /etc/logstash/conf.d/app_log.conf

RUN mkdir -p /opt/elasticsearch/indexes/.kibana

ADD /kibana/dashboard /opt/elasticsearch/indexes/.kibana
ADD /kibana/index-pattern /opt/elasticsearch/indexes/.kibana
ADD /kibana/visualization /opt/elasticsearch/indexes/.kibana


WORKDIR ${KIBANA_HOME}
RUN gosu kibana bin/kibana-plugin install https://github.com/sivasamyk/logtrail/releases/download/v0.1.25/logtrail-6.1.1-0.1.25.zip

ENV ES_HOME /opt/elasticsearch
WORKDIR ${ES_HOME}
RUN CONF_DIR=/etc/elasticsearch gosu elasticsearch bin/elasticsearch-plugin install ingest-geoip




# /var/log/ логи