package net.sergey.kosov.registrationservice.services

import net.sergey.kosov.registrationservice.domain.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class Consumer {
    @Autowired
    private Producer producer

    void send() {
        Message nextMessage = producer.getNext()
        def answer = new Message(to: nextMessage.to, mess: "Ответ на ${nextMessage.mess}", accessToken: "521122119:AAG8_db8aW50SkxqFmAx6KS-weYcn4acS7k")
        RestTemplate template = new RestTemplate()

        template.postForObject("https://api.telegram.org/bot${answer.accessToken}/sendMessage?${getParameters(answer)}", null, String.class)
    }

    private String getParameters(Message message) {
        return "text=${message.mess}&chat_id=${message.to}"
    }
}
