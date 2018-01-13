package net.sergey.kosov.telegramsender.services

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import net.sergey.kosov.telegramsender.domain.TelegramMessage
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

import javax.annotation.PostConstruct

//todo  перенести в сервис коммуникации
@Service
class Listener {
    @Autowired
    RabbitTemplate template

    private static Integer offset = 0

    @PostConstruct
    void init() {
        RestTemplate template = new RestTemplate()

        def object = template.getForObject("https://api.telegram.org/bot521122119:AAG8_db8aW50SkxqFmAx6KS-weYcn4acS7k/getUpdates?offset=${offset}", String.class)
        def text = new JsonSlurper().parseText(object)
        offset = text["result"]?.collect({ it["update_id"] })?.max()++
        def messages = text["result"]?.collect({
            new TelegramMessage(to: it["message"]["chat"]["id"], from: it["message"]["from"]["first_name"], mess: it["message"]["text"])
        })

        messages.each { this.template.convertAndSend("telegram", JsonOutput.toJson(it)) }
    }
}
