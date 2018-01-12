package net.sergey.kosov.telegramsender.services

import groovy.json.JsonSlurper
import net.sergey.kosov.telegramsender.domain.TelegramMessage
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class Consumer {

    @RabbitListener(queues = "telegram")
    private void onMessage(Message message) {
        def mess = new JsonSlurper().parseText(new String(message.getBody()))
        def answer = new TelegramMessage(to: mess["to"], mess: mess["mess"], accessToken: mess["accessToken"])
        new RestTemplate().postForObject("https://api.telegram.org/bot${answer.accessToken}/sendMessage?${getParameters(answer)}", null, String.class)
    }

    private String getParameters(TelegramMessage message) {
        return "text=${message.mess}&chat_id=${message.to}"
    }
}
