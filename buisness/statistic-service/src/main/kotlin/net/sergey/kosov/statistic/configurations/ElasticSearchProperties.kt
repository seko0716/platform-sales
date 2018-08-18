package net.sergey.kosov.statistic.configurations

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "elasticsearch")
class ElasticSearchProperties {

    lateinit var clusterName: String
    lateinit var clusterNodesStr: Array<String>
    var searchSize: Int = 11

    val clusterNodes: List<Pair<String, Int>> by lazy {
        clusterNodesStr.asSequence()
                .map { it.split(":") }
                .map { Pair(it[0], it[1].toInt()) }
                .toList()
    }
}