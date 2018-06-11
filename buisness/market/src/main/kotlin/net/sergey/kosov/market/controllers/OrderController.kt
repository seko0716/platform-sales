package net.sergey.kosov.market.controllers

import net.sergey.kosov.market.domains.Order
import net.sergey.kosov.market.domains.OrderFilter
import net.sergey.kosov.market.services.OrderService
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
class OrderController(val orderService: OrderService) {

    fun create(productId: String, count: Int, principal: Principal): Order {
        return orderService.create(productId = productId, count = count, customerName = principal.name)
    }

    fun findOrder(orderId: String): Order {
        return orderService.findOrder(orderId)
    }

    fun processOrder(orderId: String): Order {
        return orderService.processOrder(orderId)
    }

    fun completeOrder(orderId: String): Order {
        return orderService.completeOrder(orderId)
    }

    fun cancelOrder(orderId: String): Order {
        return orderService.cancelOrder(orderId)
    }

    fun findOrders(filter: OrderFilter): List<Order> {
        return orderService.findOrders(filter)
    }

}