package net.sergey.kosov.communication.services

import net.sergey.kosov.communication.api.AuthService
import net.sergey.kosov.communication.domains.Message
import net.sergey.kosov.communication.repository.MessageRepository
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CommunicationService {
    @Autowired
    lateinit var template: RabbitTemplate
    @Autowired
    lateinit var repository: MessageRepository
    @Autowired
    lateinit var authService: AuthService

    var supportedProtocols = arrayOf("telegram", "internal")

    fun createAndSend(mess: String, protocol: String, to: String): Message {
        if (!supportedProtocols.contains(protocol)) {
            throw IllegalStateException("Протокол $protocol не поддерживается. Поддерживаемые протоколы: $supportedProtocols")
        }
        val message = Message(mess = mess, to = to, protocol = protocol)
        message.accessToken = getAccessTokenByProtocol(protocol)
        val storedMessage: Message = repository.save(message)
        send(storedMessage)
        return storedMessage
    }

    private fun send(message: Message) {
        template.convertAndSend(message.protocol, message.toJson())
    }

    private fun getAccessTokenByProtocol(protocol: String): String = authService.getToken(protocol)
}

