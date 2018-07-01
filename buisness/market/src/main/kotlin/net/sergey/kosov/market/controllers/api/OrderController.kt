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

    @PostMapping("/order/processing/{orderId}")
    fun processingOrder(@PathVariable("orderId") orderId: String, principal: Principal): Order {
        return orderService.processingOrder(orderId, principal.name)
    }

    @PostMapping("/order/processed/{orderId}")
    fun processedOrder(@PathVariable("orderId") orderId: String, principal: Principal): Order {
        return orderService.processedOrder(orderId, principal.name)
    }

    @PostMapping("/order/complete/{orderId}")
    fun completeOrder(@PathVariable("orderId") orderId: String, principal: Principal): Order {
        return orderService.completeOrder(orderId, principal.name)
    }

    @PostMapping("/order/cancel/{orderId}")
    fun cancelOrder(@PathVariable("orderId") orderId: String, principal: Principal): Order {
        return orderService.cancelOrder(orderId, principal.name)
    }

    @PostMapping("/order/delete/{orderId}")
    fun deleteOrder(@PathVariable("orderId") orderId: String, principal: Principal): List<*> {
        orderService.deleteOrder(orderId, principal.name)
        return listOf<String>()
    }

    @PutMapping("/order/cart/{productId}")
    fun createOrderCart(@PathVariable("productId") productId: String, principal: Principal): Order {
        return orderService.createOrderCart(productId, principal.name)
    }

    @PostMapping("/order/cart/{orderId}/{cont}")
    fun updateOrderCart(@PathVariable("orderId") orderId: String,
                        @PathVariable("count") count: Int,
                        principal: Principal): Order {
        return orderService.updateOrderCart(orderId, count, principal.name)
    }

    @PostMapping("/order/buyCart/{orderId}")
    fun buyCart(@PathVariable("orderId") orderId: String, principal: Principal): Order {
        return orderService.buyCart(orderId, principal.name)
    }

    @GetMapping("/orders/cart")
    fun cartStatus(principal: Principal): List<Order> {
        return orderService.getCart(principal.name)
    }


}