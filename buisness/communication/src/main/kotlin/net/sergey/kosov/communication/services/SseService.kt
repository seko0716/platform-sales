package net.sergey.kosov.communication.services

import net.sergey.kosov.communication.api.AuthService
import net.sergey.kosov.communication.domains.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service


@Service
@EnableScheduling
class SseService @Autowired constructor(var accountApi: AuthService) {

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
        notify(message, "admin")
        return message
    }

    @Scheduled(fixedRate = 3000)
    fun test() {
        val message = Message(mess = "hihihihi", to = "admin", protocol = "")
        sendMessage(message)
    }
}