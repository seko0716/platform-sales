package net.sergey.kosov.communication

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class MarketApplication

fun main(args: Array<String>) {
    SpringApplication.run(MarketApplication::class.java, *args)
}