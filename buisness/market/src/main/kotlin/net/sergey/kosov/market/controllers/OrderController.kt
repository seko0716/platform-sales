package net.sergey.kosov.market.controllers

import net.sergey.kosov.market.domains.Order
import net.sergey.kosov.market.domains.Product
import net.sergey.kosov.market.domains.Status
import net.sergey.kosov.market.domains.User
import net.sergey.kosov.market.services.OrderService
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderController(val orderService: OrderService) {
    
    fun create(product: Product, count: Int, customer: User): Order {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun findOrder(orderId: String): Order {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun processOrder(order: Order): Order {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun processOrder(orderId: String): Order {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun completeOrder(order: Order): Order {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun completeOrder(orderId: String): Order {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun cancelOrder(order: Order): Order {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun cancelOrder(orderId: String): Order {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun findOrders(customer: User, status: Status?): List<Order> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}