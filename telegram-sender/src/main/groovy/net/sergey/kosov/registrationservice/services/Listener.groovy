package net.sergey.kosov.registrationservice.services

import groovy.json.JsonSlurper
import net.sergey.kosov.registrationservice.domain.Message
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

import javax.annotation.PostConstruct


//todo  перенести в сервис коммуникации
@Service
class Listener {
    private static Integer offset = 0

    @PostConstruct
    void init() {
        RestTemplate template = new RestTemplate()

        def object = template.getForObject("https://api.telegram.org/bot521122119:AAG8_db8aW50SkxqFmAx6KS-weYcn4acS7k/getUpdates?offset=${offset}", String.class)
        def text = new JsonSlurper().parseText(object)
        offset = text["result"].collect({ it["update_id"] }).max()++
        def messages = text["result"].collect({
            new Message(to: it["message"]["chat"]["id"], from: it["message"]["from"]["first_name"], mess: it["message"]["text"])
        })
//        todo положить в очередь
    }
}
