package net.sergey.kosov.communication.services

import net.sergey.kosov.communication.api.AuthService
import net.sergey.kosov.communication.domains.Message
import net.sergey.kosov.communication.domains.Status
import net.sergey.kosov.communication.repository.MessageRepository
import org.bson.types.ObjectId
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CommunicationService @Autowired constructor(var authService: AuthService,
                                                  var repository: MessageRepository,
                                                  var template: RabbitTemplate) {

    var supportedProtocols = arrayOf("telegram", "internal")

    fun createAndSend(mess: String, protocol: String, to: String): Message {
        if (!supportedProtocols.contains(protocol)) {
            throw IllegalStateException("Протокол $protocol не поддерживается. Поддерживаемые протоколы: $supportedProtocols")
        }
        val message = Message(mess = mess, to = to, protocol = protocol)
        message.accessToken = getAccessTokenByProtocol(protocol)
        val storedMessage = repository.save(message)
        send(storedMessage)
        return storedMessage
    }

    private fun send(message: Message) {
        template.convertAndSend(message.protocol, message.toJson())
    }

    private fun getAccessTokenByProtocol(protocol: String): String = authService.getToken(protocol)

    fun completeMessage(id: ObjectId) {
        val message: Message = repository.findOne(id)
        repository.save(message.apply {
            this.status = Status.COMPLETED
            this.completedDate = LocalDateTime.now()
        })
    }
}

