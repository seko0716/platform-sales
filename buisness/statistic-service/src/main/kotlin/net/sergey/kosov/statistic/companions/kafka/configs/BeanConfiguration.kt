package net.sergey.kosov.statistic.companions.kafka.configs

import net.sergey.kosov.statistic.companions.kafka.KafkaSink
import net.sergey.kosov.statistic.companions.kafka.ProductRecommendation
import net.sergey.kosov.statistic.companions.kafka.serialization.KafkaDataJsonDeserializer
import net.sergey.kosov.statistic.companions.kafka.serialization.KafkaDataJsonSerializer
import net.sergey.kosov.statistic.domains.Product
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.apache.spark.SparkConf
import org.apache.spark.api.java.JavaSparkContext
import org.apache.spark.api.java.Optional
import org.apache.spark.streaming.Durations
import org.apache.spark.streaming.State
import org.apache.spark.streaming.api.java.JavaStreamingContext
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import scala.Tuple2
import java.io.Serializable

@ComponentScan(basePackages = ["net.sergey.kosov.statistic.companions.kafka"])
@Configuration
class BeanConfiguration {

    @Bean
    fun sparkConf(sparkProperties: SparkProperties): SparkConf {
        return SparkConf().setAppName(sparkProperties.appName).setMaster(sparkProperties.master)
    }

    @Bean
    fun javaStreamingContext(sparkProperties: SparkProperties): JavaStreamingContext {
        val jssc = JavaStreamingContext(javaSparkContext(sparkProperties), Durations.seconds(sparkProperties.durations))
        jssc.checkpoint(sparkProperties.checkpointDirectory)
        return jssc
    }

    @Bean
    fun javaSparkContext(sparkProperties: SparkProperties): JavaSparkContext {
        return JavaSparkContext(sparkConf(sparkProperties))
    }

    @Bean
    fun kafkaSink(kafkaProperties: KafkaProperties): KafkaSink {
        return KafkaSink(kafkaProperties, createProducer = { config -> KafkaProducer(config) })
    }

    @Bean
    fun mappingFunc(): (Product, Optional<List<Tuple2<Product, Int>>>, State<List<Tuple2<Product, Int>>>) -> Tuple2<Product, List<Tuple2<Product, Int>>> {
        return { productId, currentValue, state ->
            val stateValue = if (state.exists()) state.get() else listOf()//[(12,3),(11,3),(15,2),(1,4)]

            val map = mutableMapOf<Product, Int>()
            (currentValue.orElse(listOf()) + stateValue).forEach {
                map[it._1] = map.getOrDefault(it._1, 0) + it._2
            }
            val sum = map.map { Tuple2(it.key, it.value) }

            val output = Tuple2(productId, sum)
            state.update(sum)
            output
        }
    }

    @Bean
    fun productRecommendation(kafkaProperties: KafkaProperties, javaSparkContext: JavaSparkContext): ProductRecommendation {
        val kafkaSink = javaSparkContext.broadcast(kafkaSink(kafkaProperties))
        val mappingFunc = javaSparkContext.broadcast(mappingFunc())
        return ProductRecommendation(kafkaSink, mappingFunc)
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

