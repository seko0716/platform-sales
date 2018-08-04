package net.sergey.kosov.internalsender

import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
@EnableRabbit
class InternalSenderApplication {
}

fun main(args: Array<String>) {
    SpringApplication.run(InternalSenderApplication::class.java, *args)
}