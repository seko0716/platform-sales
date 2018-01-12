package net.sergey.kosov.configurationservice

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.config.server.EnableConfigServer

@SpringBootApplication
@EnableConfigServer
class ConfigApplications {
    static void main(String[] args) {
        SpringApplication.run ConfigApplications.class, args
    }
}
