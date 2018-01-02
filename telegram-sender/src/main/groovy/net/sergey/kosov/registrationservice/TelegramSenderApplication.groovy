package net.sergey.kosov.registrationservice

import net.sergey.kosov.registrationservice.services.Consumer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

import javax.annotation.PostConstruct

@SpringBootApplication
class TelegramSenderApplication {
    @Autowired
    Consumer consumer

    static void main(String[] args) {
        SpringApplication.run TelegramSenderApplication.class, args
    }

    @PostConstruct
    void init() {
        consumer.send()
    }
}
