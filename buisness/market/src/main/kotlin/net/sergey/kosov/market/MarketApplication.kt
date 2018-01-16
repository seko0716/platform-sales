package net.sergey.kosov.market

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.data.convert.Jsr310Converters

@SpringBootApplication
@EntityScan(value = ["net.sergey.kosov.market.domains"], basePackageClasses = [Jsr310Converters::class])
class MarketApplication

fun main(args: Array<String>) {
    SpringApplication.run(MarketApplication::class.java, *args)
}