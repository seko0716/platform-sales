package net.sergey.kosov.statistic.configurations

import org.elasticsearch.client.Client
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.TransportAddress
import org.elasticsearch.transport.client.PreBuiltTransportClient
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.net.InetSocketAddress

@Configuration
@EnableConfigurationProperties(ElasticSearchProperties::class)
class ElasticSearchConfiguration {

    @Bean
    fun elasticsearchClient(elasticSearchProperties: ElasticSearchProperties): Client {
        val settings = Settings.builder()
                .put("cluster.name", elasticSearchProperties.clusterName)
                .build()
        System.setProperty("es.set.netty.runtime.available.processors", "false")// без этого ошибка - availableProcessors is already set to [4], rejecting [4] elasticsearch
        val client = PreBuiltTransportClient(settings)
        elasticSearchProperties.clusterNodes
                .forEach { client.addTransportAddress(TransportAddress(InetSocketAddress(it.first, it.second))) }
        return client
    }

}

