package net.sergey.kosov.statistic.companions.kafka

import net.sergey.kosov.common.annotations.PostRefresh
import net.sergey.kosov.statistic.companions.kafka.configs.KafkaProperties
import net.sergey.kosov.statistic.domains.KafkaData
import org.apache.spark.streaming.api.java.JavaStreamingContext
import org.apache.spark.streaming.kafka010.ConsumerStrategies
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.spark.streaming.kafka010.LocationStrategies
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProductRecommendationService @Autowired constructor(private var jssc: JavaStreamingContext,
                                                          private var kafProperties: KafkaProperties,
                                                          private var productRecommendation: ProductRecommendation) {
    @PostRefresh
    fun init() {
        val preferConsistent = LocationStrategies.PreferConsistent()
        val subscribe = ConsumerStrategies.Subscribe<String, KafkaData>(kafProperties.consumerTopics.toList(), kafProperties.kafkaConsumerProperties)
        val stream = KafkaUtils.createDirectStream(jssc, preferConsistent, subscribe)
        Thread(Runnable {
            productRecommendation.calculate(stream, jssc)
        }).start()


    }
}