package net.sergey.kosov.market.services

import net.sergey.kosov.market.domains.Order
import net.sergey.kosov.market.domains.Product
import net.sergey.kosov.market.domains.Status
import net.sergey.kosov.market.domains.User
import org.bson.types.ObjectId


interface OrderService {
    fun create(product: Product, count: Int = 1, customer: User): Order
    fun findOrder(orderId: ObjectId): Order
    fun processOrder(order: Order): Order
    fun processOrder(orderId: ObjectId): Order
    fun completeOrder(order: Order): Order
    fun completeOrder(orderId: ObjectId): Order
    fun cancelOrder(order: Order): Order
    fun cancelOrder(orderId: ObjectId): Order
    fun findOrders(customer: User, status: Status? = null): List<Order>
}