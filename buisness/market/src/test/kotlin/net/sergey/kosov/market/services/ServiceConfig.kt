package net.sergey.kosov.market.services

import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer
import org.springframework.cloud.netflix.feign.FeignAutoConfiguration
import org.springframework.cloud.netflix.feign.ribbon.FeignRibbonClientAutoConfiguration
import org.springframework.cloud.netflix.ribbon.RibbonAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import java.util.*


@SpringBootConfiguration
@DataMongoTest
@EntityScan(value = ["net.sergey.kosov.market.domains"]/*, basePackageClasses = {Jsr310JpaConverters.class}*/)
@EnableMongoRepositories(basePackages = ["net.sergey.kosov.market.repository"])
@ComponentScan(basePackages = ["net.sergey.kosov.market.services", "net.sergey.kosov.market.repository", "net.sergey.kosov.market.api"])
@ContextConfiguration(
        initializers = [ConfigFileApplicationContextInitializer::class])
@TestPropertySource(properties = ["chart.size=100"])
@ImportAutoConfiguration(RibbonAutoConfiguration::class, FeignRibbonClientAutoConfiguration::class, FeignAutoConfiguration::class)
class ServiceConfig {
    @Bean
    fun properties(): PropertySourcesPlaceholderConfigurer {
        val properties = Properties()
        properties.setProperty("chart.size", "100")

        return PropertySourcesPlaceholderConfigurer().apply { this.setProperties(properties) }

    }
}