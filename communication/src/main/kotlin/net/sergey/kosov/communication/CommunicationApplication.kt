package net.sergey.kosov.communication

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication


@SpringBootApplication
class CommunicationApplication

fun main(args: Array<String>) {
    SpringApplication.run(CommunicationApplication::class.java, *args)
}