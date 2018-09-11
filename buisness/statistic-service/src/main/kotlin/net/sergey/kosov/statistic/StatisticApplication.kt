package net.sergey.kosov.statistic

import net.sergey.kosov.common.listeners.ContextRefreshListener
import net.sergey.kosov.statistic.companions.KafkaProperties
import net.sergey.kosov.statistic.domains.KafkaData
import net.sergey.kosov.statistic.serialization.KafkaDataJsonDeserializer
import net.sergey.kosov.statistic.serialization.KafkaDataJsonSerializer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.Bean
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import java.io.Serializable


@SpringBootApplication
@EnableOAuth2Client
@EnableDiscoveryClient
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
class StatisticApplication {
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

    @Bean
    fun contextRefreshListener(beanFactory: ConfigurableListableBeanFactory): ContextRefreshListener {
        return ContextRefreshListener(beanFactory)
    }

    @Bean
    @Qualifier("kafkaConsumerProperties")
    fun kafkaConsumerProperties(@Value("\${kafka.brokers}") brokers: String,
                                @Value("\${kafka.groupId}") groupId: String): Map<String, Serializable> {
        return mapOf(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to brokers,
                ConsumerConfig.GROUP_ID_CONFIG to groupId,
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to KafkaDataJsonDeserializer::class.java)
    }

    @Bean
    @Qualifier("kafkaProducerProperties")
    fun kafkaProducerProperties(@Value("\${kafka.brokers}") brokers: String): MutableMap<String, String> {
        return mutableMapOf(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to brokers,
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java.name,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to KafkaDataJsonSerializer::class.java.name)
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(StatisticApplication::class.java, *args)
}


