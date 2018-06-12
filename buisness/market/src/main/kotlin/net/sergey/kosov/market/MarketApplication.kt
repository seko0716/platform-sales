package net.sergey.kosov.market

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client


@EnableOAuth2Client
//@EnableResourceServer //todo revert for oAuth. comment for base64
@SpringBootApplication
class MarketApplication

fun main(args: Array<String>) {
    SpringApplication.run(MarketApplication::class.java, *args)
}