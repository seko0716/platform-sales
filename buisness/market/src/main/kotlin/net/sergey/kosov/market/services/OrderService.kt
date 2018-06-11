package net.sergey.kosov.market.services

import net.sergey.kosov.market.domains.Order
import net.sergey.kosov.market.domains.OrderFilter

interface OrderService {
    fun create(productId: String, count: Int = 1, customerName: String): Order
    fun findOrder(orderId: String): Order
    fun processOrder(orderId: String): Order
    fun completeOrder(orderId: String): Order
    fun cancelOrder(orderId: String): Order
    fun findOrders(filter: OrderFilter): List<Order>
}