package net.sergey.kosov.companion.kafka.configs

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class SparkProperties {
    @Value("\${spark.master}")
    lateinit var master: String //"local[*]"
    @Value("\${spark.appName}")
    lateinit var appName: String //"ProductRecommendation"
    @Value("\${spark.checkpointDirectory}")
    lateinit var checkpointDirectory: String //"./checkpoint"
    @Value("\${spark.durations}")
    var durations: Long = 5 //30l
}