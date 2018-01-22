package net.sergey.kosov.market.services

import net.sergey.kosov.market.domains.Goods
import net.sergey.kosov.market.domains.Order
import net.sergey.kosov.market.domains.User
import org.bson.types.ObjectId


interface OrderService {
    fun create(goods: Goods, count: Int = 1, customer: User): Order
    fun findOrder(orderId: ObjectId): Order
    fun processOrder(order: Order)
    fun processOrder(orderId: ObjectId)
    fun completeOrder(order: Order)
}