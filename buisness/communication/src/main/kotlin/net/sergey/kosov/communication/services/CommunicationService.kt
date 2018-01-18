package net.sergey.kosov.communication.services

import net.sergey.kosov.communication.api.AuthService
import net.sergey.kosov.communication.domains.Message
import net.sergey.kosov.communication.domains.Status
import net.sergey.kosov.communication.repository.MessageRepository
import org.bson.types.ObjectId
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class CommunicationService @Autowired constructor(var authService: AuthService,
                                                  var repository: MessageRepository,
                                                  var template: RabbitTemplate) {

    private val log = LoggerFactory.getLogger(javaClass)

    var supportedProtocols = arrayOf("telegram", "internal")

    @Transactional(propagation = Propagation.REQUIRED)
    fun createAndSend(mess: String, protocol: String, to: String): Message {
        if (!supportedProtocols.contains(protocol)) {
            log.warn("Протокол {} не поддерживается. Поддерживаемые протоколы: {}", protocol, supportedProtocols)
            throw IllegalStateException("Протокол $protocol не поддерживается. Поддерживаемые протоколы: $supportedProtocols")
        }
        val message = Message(mess = mess, to = to, protocol = protocol)
        val accessToken = getAccessTokenByProtocol(protocol)
        if (accessToken.isBlank()) {
            log.warn("Пустой accessToken")
            return message.apply { this.status = Status.ERROR }
        }
        message.accessToken = accessToken
        val storedMessage = repository.save(message)
        send(storedMessage)
        return storedMessage.apply { this.status = Status.SANDING }
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

