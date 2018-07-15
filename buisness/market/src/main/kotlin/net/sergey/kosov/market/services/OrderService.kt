package net.sergey.kosov.market.services

import net.sergey.kosov.market.domains.entity.Order
import net.sergey.kosov.market.domains.view.wrappers.OrderViewCreation

interface OrderService {
    fun create(orderViewCreation: OrderViewCreation, customerName: String): Order
    fun findOrder(orderId: String, userName: String): Order
    fun processingOrder(orderId: String, userName: String): Order
    fun completeOrder(orderId: String, userName: String): Order
    fun cancelOrder(orderId: String, userName: String): Order
    fun getOrders(customerName: String): List<Order>
    fun createOrderCart(productId: String, userName: String): Order
    fun updateOrderCart(orderId: String, count: Int, userName: String): Order
    fun processedOrder(orderId: String, userName: String): Order
    fun deleteOrder(orderId: String, userName: String)
    fun getCart(userName: String): List<Order>
    fun buyCart(orderId: String, userName: String): Order
}