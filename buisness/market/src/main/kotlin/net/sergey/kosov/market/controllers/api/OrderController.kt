package net.sergey.kosov.market.controllers.api

import net.sergey.kosov.market.domains.entity.Order
import net.sergey.kosov.market.domains.view.wrappers.OrderViewCreation
import net.sergey.kosov.market.services.OrderService
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
class OrderController(val orderService: OrderService) {

    @PutMapping("/order")
    fun create(@RequestBody orderViewCreation: OrderViewCreation, principal: Principal): Order {
        return orderService.create(orderViewCreation = orderViewCreation, customerName = principal.name)
    }

    @GetMapping("/orders")
    fun getOrders(principal: Principal): List<Order> {
        return orderService.getOrders(customerName = principal.name)
    }

    @GetMapping("/order/{orderId}")
    fun findOrder(@PathVariable("orderId") orderId: String, principal: Principal): Order {
        return orderService.findOrder(orderId, principal.name)
    }

    @PostMapping("/order/process/{orderId}")
    fun processOrder(@PathVariable("orderId") orderId: String, principal: Principal): Order {
        return orderService.processOrder(orderId, principal.name)
    }

    @PostMapping("/order/complete/{orderId}")
    fun completeOrder(@PathVariable("orderId") orderId: String, principal: Principal): Order {
        return orderService.completeOrder(orderId, principal.name)
    }

    @PostMapping("/order/cancel/{orderId}")
    fun cancelOrder(@PathVariable("orderId") orderId: String, principal: Principal): Order {
        return orderService.cancelOrder(orderId, principal.name)
    }

}