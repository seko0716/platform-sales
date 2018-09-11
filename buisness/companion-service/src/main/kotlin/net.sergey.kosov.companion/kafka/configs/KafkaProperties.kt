package net.sergey.kosov.companion.kafka.configs

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.Serializable

@Component
class KafkaProperties : Serializable {
    @Value("\${kafka.brokers}")
    lateinit var brokers: String //= "192.168.0.106:9092"
    @Value("\${kafka.groupId}")
    lateinit var groupId: String // = "wc"
    @Value("\${kafka.outputTopic}")
    lateinit var outputTopic: String // = "wc"
    @Value("\${kafka.consumerTopics}")
    lateinit var consumerTopics: Array<String> //= "wc-topic"
    @Value("\${kafka.procurerTopics}")
    lateinit var procurerTopics: Array<String> //= "wc-topic"

    @Autowired
    @Qualifier("kafkaConsumerProperties")
    lateinit var kafkaConsumerProperties: Map<String, java.io.Serializable>
    @Autowired
    @Qualifier("kafkaProducerProperties")
    lateinit var kafkaProducerProperties: MutableMap<String, String>
}