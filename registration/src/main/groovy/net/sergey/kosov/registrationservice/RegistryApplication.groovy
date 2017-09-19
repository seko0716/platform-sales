package net.sergey.kosov.registrationservice

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer

@SpringBootApplication
@EnableEurekaServer
class RegistryApplication {
    static void main(String[] args) {
        SpringApplication.run(RegistryApplication.class, args)
    }
}