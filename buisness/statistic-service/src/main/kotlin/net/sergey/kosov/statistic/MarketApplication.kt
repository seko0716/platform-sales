package net.sergey.kosov.statistic

import net.sergey.kosov.statistic.companions.kafka.configs.KafkaProperties
import net.sergey.kosov.statistic.domains.KafkaData
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer


@SpringBootApplication
class MarketApplication {
    @Bean
    fun producerFactory(kafkaProperties: KafkaProperties): ProducerFactory<String, KafkaData> {
        return DefaultKafkaProducerFactory(producerConfigs(kafkaProperties))
    }

    @Bean
    fun producerConfigs(kafkaProperties: KafkaProperties): Map<String, Any> {
        val props = HashMap<String, Any>()
        props[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaProperties.brokers
        props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = JsonSerializer::class.java
        // See https://kafka.apache.org/documentation/#producerconfigs for more properties
        return props
    }

    @Bean
    fun kafkaTemplate(kafkaProperties: KafkaProperties): KafkaTemplate<String, KafkaData> {
        return KafkaTemplate(producerFactory(kafkaProperties))
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(MarketApplication::class.java, *args)
}