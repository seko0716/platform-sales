package net.sergey.kosov.statistic.companions.kafka

import net.sergey.kosov.statistic.companions.kafka.configs.KafkaProperties
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import scala.Serializable
import scala.Tuple2
import java.util.concurrent.Future

class KafkaSink(var kafkaProperties: KafkaProperties, var createProducer: (kafkaProperties: Map<String, String>) -> KafkaProducer<String, String>) : Serializable {

    private var producer: KafkaProducer<String, String>? = null

    fun send(value: Tuple2<Any, List<Tuple2<Any, Int>>>): Future<RecordMetadata> {
        if (producer == null) {// потому что надо как-то сериализовать несириализуемое....
            producer = createProducer(kafkaProperties.kafkaProducerProperties)
        }
        return producer?.send(ProducerRecord(kafkaProperties.outputTopic, value.toString()))!!
    }
}