package net.sergey.kosov.registrationservice.services

import net.sergey.kosov.registrationservice.domain.Message
import org.springframework.stereotype.Service

@Service
class Producer {
    Message getNext() {
//        todo тут достам сообщения из MQ
        return Message.builder().mess("message1").to("420950113").build()
    }
}
