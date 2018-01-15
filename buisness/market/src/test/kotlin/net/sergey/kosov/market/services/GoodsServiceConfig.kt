package net.sergey.kosov.market.services

import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootConfiguration
@DataMongoTest
@EntityScan(value = "net.sergey.kosov.market.domains"/*, basePackageClasses = {Jsr310JpaConverters.class}*/)
@EnableMongoRepositories(basePackages = ["net.sergey.kosov.market.repository"])
@ComponentScan(basePackages = ["net.sergey.kosov.market.services"])
class GoodsServiceConfig {
}