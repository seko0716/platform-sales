package net.sergey.kosov.market.services

import net.sergey.kosov.market.domains.Order
import net.sergey.kosov.market.domains.Product
import net.sergey.kosov.market.domains.Status
import net.sergey.kosov.market.domains.Status.*
import net.sergey.kosov.market.domains.User
import net.sergey.kosov.market.repository.order.OrderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class MarketOrderService @Autowired constructor(var orderRepository: OrderRepository) : OrderService {
    private val cancelableStatuses = listOf(PROCESSING, CREATED)
    private val completeStatuses = listOf(PROCESSING)
    private val processStatuses = listOf(CREATED)

    override fun create(product: Product, count: Int, customer: User): Order {
        return orderRepository.insert(Order(product = product, count = count, customer = customer))
    }

    override fun findOrder(orderId: String): Order {
        return orderRepository.findOne(orderId)
    }

    override fun processOrder(orderId: String): Order {
        val order = findOrder(orderId)
        return orderRepository.save(changeStatus(order, PROCESSING))
    }

    override fun completeOrder(orderId: String): Order {
        val order = findOrder(orderId)
        return orderRepository.save(changeStatus(order, COMPLETED))
    }

    override fun cancelOrder(orderId: String): Order {
        val order = findOrder(orderId)
        return orderRepository.save(changeStatus(order, CANCELED))
    }

    override fun findOrders(customer: User, status: Status?): List<Order> {
        val query = Query(Criteria.where("customer").`is`(customer))
        if (status != null) {
            query.addCriteria(Criteria.where("status").`is`(status))
        }
        return orderRepository.findByQuery(query)
    }

    private fun changeStatus(order: Order, status: Status): Order {
        if (order.statusHistory.any { it.first == COMPLETED }) {
            throw IllegalStateException("Нельзя сетить статус после $COMPLETED-- ордер звершен")
        }

        when (status) {
            CREATED -> throw IllegalStateException("Нельзя сетить статус $CREATED, он заполняется только при создании ордера")
            COMPLETED -> {
                if (order.status !in completeStatuses) {
                    throw IllegalArgumentException("Комплитить можно только ордера в статусе $completeStatuses")
                }
                order.completedTime = LocalDateTime.now()
            }
            PROCESSING -> {
                if (order.status !in processStatuses) {
                    throw IllegalArgumentException("Процессить можно только ордера в статусе $processStatuses")
                }
            }
            CANCELED -> {
                if (order.status !in cancelableStatuses) {
                    throw IllegalArgumentException("Отменять можно только ордера в статусе $cancelableStatuses")
                }
            }
        }

        return order.apply {
            this.status = status
            this.statusHistory.add(Pair(status, LocalDateTime.now()))
        }
    }
}