package net.sergey.kosov.communication.configuration

import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean

//@Configuration
class ConfigurationMQ {
    @Value("\${hostname}")
    lateinit var hostname: String

    //настраиваем соединение с RabbitMQ
    @Bean
    fun connectionFactory(): ConnectionFactory {
        return CachingConnectionFactory(hostname)
    }

    @Bean
    fun amqpAdmin(): AmqpAdmin {
        return RabbitAdmin(connectionFactory())
    }

    @Bean
    fun rabbitTemplate(): RabbitTemplate {
        return RabbitTemplate(connectionFactory())
    }

    //объявляем очередь с именем telegram
    @Bean
    fun telegramQueue(): Queue {
        return Queue("telegram")
    }

    @Bean
    fun internalQueue(): Queue {
        return Queue("internal")
    }
}