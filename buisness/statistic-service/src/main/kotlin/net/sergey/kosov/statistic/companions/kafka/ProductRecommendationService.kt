package net.sergey.kosov.statistic.companions.kafka

import net.sergey.kosov.statistic.companions.kafka.configs.KafkaProperties
import net.sergey.kosov.statistic.domains.KafkaData
import org.apache.spark.streaming.api.java.JavaStreamingContext
import org.apache.spark.streaming.kafka010.ConsumerStrategies
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.spark.streaming.kafka010.LocationStrategies
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class ProductRecommendationService @Autowired constructor(private var jssc: JavaStreamingContext,
                                                          private var kafProperties: KafkaProperties,
                                                          private var productRecommendation: ProductRecommendation) {
    @PostConstruct
    fun init() {
        val preferConsistent = LocationStrategies.PreferConsistent()
        val subscribe = ConsumerStrategies.Subscribe<String, KafkaData>(kafProperties.inputTopics.toList(), kafProperties.kafkaConsumerProperties)
        val stream = KafkaUtils.createDirectStream(jssc, preferConsistent, subscribe)
        productRecommendation.calculate(stream, jssc)

    }
}