package net.sergey.kosov.internalsender.services

import net.sergey.kosov.internalsender.domains.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service

@Service
class Consumer {

    @RabbitListener(queues = ["internal"])
    fun onMessage(message: Message) {
        println(message)
    }
}