package net.sergey.kosov.monitoringservice

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard
import org.springframework.cloud.netflix.turbine.stream.EnableTurbineStream

@SpringBootApplication
@EnableTurbineStream
@EnableHystrixDashboard
class MonitoringApplication {
    static void main(String[] args) {
        SpringApplication.run MonitoringApplication.class, args
    }
}