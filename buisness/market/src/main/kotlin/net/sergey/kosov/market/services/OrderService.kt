package net.sergey.kosov.market.services

import net.sergey.kosov.market.domains.entity.Order
import net.sergey.kosov.market.domains.view.wrappers.OrderViewCreation

interface OrderService {
    fun create(orderViewCreation: OrderViewCreation, customerName: String): Order
    fun findOrder(orderId: String, name: String): Order
    fun processingOrder(orderId: String, name: String): Order
    fun completeOrder(orderId: String, name: String): Order
    fun cancelOrder(orderId: String, name: String): Order
    fun getOrders(customerName: String): List<Order>
    fun createOrderCart(productId: String, name: String): Order
    fun updateOrderCart(orderId: String, count: Int, name: String): Order
    fun processedOrder(orderId: String, name: String): Order
    fun deleteOrder(orderId: String, name: String)
    fun getCart(name: String): List<Order>
}