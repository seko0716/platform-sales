package net.sergey.kosov.communication.services

import net.sergey.kosov.communication.api.AccountApi
import net.sergey.kosov.communication.api.AuthService
import net.sergey.kosov.communication.domains.Message
import net.sergey.kosov.communication.domains.MessageType.*
import net.sergey.kosov.communication.domains.Status
import net.sergey.kosov.communication.domains.ViewMessageCreation
import net.sergey.kosov.communication.repository.MessageRepository
import org.bson.types.ObjectId
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class MessageService @Autowired constructor(var authService: AuthService,
                                            var accountApi: AccountApi,
                                            var sendingService: SendingService,
                                            var repository: MessageRepository) {
    private val log = LoggerFactory.getLogger(javaClass)

    fun completeMessage(id: ObjectId) {
        val message: Message = repository.findOne(id)
        repository.save(message.apply {
            this.status = Status.COMPLETED
            this.completedDate = LocalDateTime.now()
        })
    }

    fun createAndSend(viewMessageCreation: ViewMessageCreation, from: String = "internal"): Message {
        val message: Message = when (viewMessageCreation.type) {
            ORDER_COMMENT -> {
                createOrderComment(viewMessageCreation, from)
            }
            PRODUCT_COMMENT -> {
                createProductComment(viewMessageCreation, from)
            }
            PERSONAL -> {
                createPersonalMessage(viewMessageCreation, from)
            }
            MARKET_EVENT -> {
                createMarketEvent(viewMessageCreation, from)
            }
            INTERNAL_EVENT -> {
                createInternalEvent(viewMessageCreation, from)
            }
        }
        return sendingService.send(message)
    }

    private fun createInternalEvent(creation: ViewMessageCreation, from: String): Message {
        val message = Message(to = listOf(creation.to), from = from, mess = creation.mess, protocol = creation.protocol)
        return repository.save(message)
    }

    private fun createMarketEvent(creation: ViewMessageCreation, from: String): Message {
        val marketName = creation.to
        val account = accountApi.getAccount(marketName)
        val message = Message(to = account.users, from = from, mess = creation.mess, protocol = creation.protocol)
        return repository.save(message)
    }

    private fun createPersonalMessage(creation: ViewMessageCreation, from: String): Message {
        val userEmail = creation.to
        val user = accountApi.getUser(userEmail)
        val message = Message(to = listOf(user.email), from = from, mess = creation.mess, protocol = creation.protocol)
        return repository.save(message)
    }

    private fun createProductComment(creation: ViewMessageCreation, from: String): Message {
        val marketName = creation.to
        val account = accountApi.getAccount(marketName)
        val message = Message(to = account.users, from = from, mess = creation.mess, productId = ObjectId(creation.entityId), protocol = creation.protocol)
        return repository.save(message)
    }

    private fun createOrderComment(creation: ViewMessageCreation, from: String): Message {
        val marketName = creation.to
        val account = accountApi.getAccount(marketName)
        val message = Message(to = account.users, from = from, mess = creation.mess, orderId = ObjectId(creation.entityId), protocol = creation.protocol)
        return repository.save(message)
    }
}

