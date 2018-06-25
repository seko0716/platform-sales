package net.sergey.kosov.market

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class AccountApplication {
}

fun main(args: Array<String>) {
    SpringApplication.run(AccountApplication::class.java, *args)
}