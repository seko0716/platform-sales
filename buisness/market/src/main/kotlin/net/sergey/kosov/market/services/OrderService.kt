package net.sergey.kosov.market.services

import net.sergey.kosov.market.domains.entity.Order
import net.sergey.kosov.market.domains.view.wrappers.OrderFilter
import net.sergey.kosov.market.domains.view.wrappers.OrderViewCreation

interface OrderService {
    fun create(orderViewCreation: OrderViewCreation, customerName: String): Order
    fun findOrder(orderId: String): Order
    fun processOrder(orderId: String): Order
    fun completeOrder(orderId: String): Order
    fun cancelOrder(orderId: String): Order
    fun findOrders(filter: OrderFilter): List<Order>
}