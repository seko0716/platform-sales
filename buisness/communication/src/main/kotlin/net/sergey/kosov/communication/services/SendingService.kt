package net.sergey.kosov.communication.services

import net.sergey.kosov.communication.domains.Message
import net.sergey.kosov.communication.domains.Status
import org.slf4j.LoggerFactory
import org.springframework.amqp.AmqpException
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SendingService @Autowired constructor(var template: RabbitTemplate) {
    private val log = LoggerFactory.getLogger(javaClass)

    fun send(message: Message): Message {
        return try {
            template.convertAndSend(message.protocol, message)
            message
        } catch (e: AmqpException) {
            log.warn("проблемы с AMQ", e)
            message.apply { this.status = Status.ERROR }
        }
    }
}