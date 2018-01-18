FROM sebp/elk

ADD /logstash/app_log.conf /etc/logstash/conf.d/app_log.conf

#logstash.yml конфигурация ls


WORKDIR ${KIBANA_HOME}
RUN gosu kibana bin/kibana-plugin install https://github.com/sivasamyk/logtrail/releases/download/v0.1.25/logtrail-6.1.1-0.1.25.zip