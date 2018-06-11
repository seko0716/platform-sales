package net.sergey.kosov.market.services

import net.sergey.kosov.market.domains.Order
import net.sergey.kosov.market.domains.Product
import net.sergey.kosov.market.domains.Status
import net.sergey.kosov.market.domains.User

interface OrderService {
    fun create(product: Product, count: Int = 1, customer: User): Order
    fun findOrder(orderId: String): Order
    fun processOrder(orderId: String): Order
    fun completeOrder(orderId: String): Order
    fun cancelOrder(orderId: String): Order
    fun findOrders(customer: User, status: Status? = null): List<Order>
}