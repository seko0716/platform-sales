package net.sergey.kosov.telegramsender

import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
@EnableRabbit
class TelegramSenderApplication {
    static void main(String[] args) {
        SpringApplication.run TelegramSenderApplication.class, args
    }
}
