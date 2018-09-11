package net.sergey.kosov.companion


import net.sergey.kosov.common.listeners.ContextRefreshListener
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer

@SpringBootApplication
@EnableOAuth2Client
@EnableDiscoveryClient
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
class CompanionApplication {
    @Bean
    fun contextRefreshListener(beanFactory: ConfigurableListableBeanFactory): ContextRefreshListener {
        return ContextRefreshListener(beanFactory)
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(CompanionApplication::class.java, *args)
}