package net.sergey.kosov.apigetewaynservice

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.zuul.EnableZuulProxy

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
class ApiGatewayApplication {
    static void main(String[] args) {
        SpringApplication.run ApiGatewayApplication.class, args
    }
}
