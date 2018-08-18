package net.sergey.kosov.statistic.companions.kafka

import net.sergey.kosov.statistic.companions.kafka.configs.KafkaProperties
import net.sergey.kosov.statistic.companions.kafka.domains.KafkaProductResult
import net.sergey.kosov.statistic.companions.kafka.domains.ProductCount
import net.sergey.kosov.statistic.domains.Product
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import scala.Serializable
import scala.Tuple2
import java.util.concurrent.Future

class KafkaSink(var kafkaProperties: KafkaProperties,
                var createProducer: (kafkaProperties: Map<String, String>)
                -> KafkaProducer<String, KafkaProductResult>) : Serializable {

    private lateinit var producer: KafkaProducer<String, KafkaProductResult>

    fun send(value: Tuple2<Product, List<Tuple2<Product, Int>>>): Future<RecordMetadata> {
        if (!::producer.isInitialized) {// потому что надо как-то сериализовать несириализуемое....
            producer = createProducer(kafkaProperties.kafkaProducerProperties)
        }

        val result: KafkaProductResult = createResult(value)

        return producer.send(ProducerRecord(kafkaProperties.outputTopic, result))!!
    }

    private fun createResult(value: Tuple2<Product, List<Tuple2<Product, Int>>>): KafkaProductResult {
        val companions = value._2.map { ProductCount(it._1, it._2) }
        return KafkaProductResult(value._1, companions)
    }
}

