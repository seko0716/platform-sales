package net.sergey.kosov.prediction.configuration

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
    @Value("\${spark.modelStorage}")
    lateinit var modelStorage: String
    @Value("\${spark.train}")
    var train: Double = 0.7
    @Value("\${spark.test}")
    var test: Double = 0.3

}