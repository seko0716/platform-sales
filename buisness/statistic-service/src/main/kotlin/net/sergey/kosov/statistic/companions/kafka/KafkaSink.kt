package net.sergey.kosov.statistic.companions.kafka

import net.sergey.kosov.statistic.companions.kafka.configs.KafkaProperties
import net.sergey.kosov.statistic.domains.Product
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import scala.Serializable
import scala.Tuple2
import java.util.concurrent.Future

class KafkaSink(var kafkaProperties: KafkaProperties,
                var createProducer: (kafkaProperties: Map<String, String>)
                -> KafkaProducer<String, Tuple2<Product, List<Tuple2<Product, Int>>>>) : Serializable {

    private var producer: KafkaProducer<String, Tuple2<Product, List<Tuple2<Product, Int>>>>? = null

    fun send(value: Tuple2<Product, List<Tuple2<Product, Int>>>): Future<RecordMetadata> {
        if (producer == null) {// потому что надо как-то сериализовать несириализуемое....
            producer = createProducer(kafkaProperties.kafkaProducerProperties)
        }
        return producer?.send(ProducerRecord(kafkaProperties.outputTopic, value))!!
    }
}