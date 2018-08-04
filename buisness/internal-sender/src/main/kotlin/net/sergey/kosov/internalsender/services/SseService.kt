package net.sergey.kosov.internalsender.services

import net.sergey.kosov.internalsender.domains.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service


@Service
class SseService {

    @Autowired
    lateinit var messagingTemplate: SimpMessagingTemplate

    /**
     * Send notification to users subscribed on channel "/user/queue/notify".
     *
     * The message will be sent only to the user with the given username.
     *
     * @param notification The notification message.
     * @param username The username for the user to send notification.
     */
    fun notify(notification: Message, username: String) {
        messagingTemplate.convertAndSendToUser(
                username,
                "/queue/notify",
                notification
        )
    }


    fun sendMessage(message: Message): Message {
        message.to.forEach {
            notify(message, it)
        }
        return message
    }
}