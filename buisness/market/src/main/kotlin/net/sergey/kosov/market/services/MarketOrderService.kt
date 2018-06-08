package net.sergey.kosov.market.services

import net.sergey.kosov.market.domains.Order
import net.sergey.kosov.market.domains.Product
import net.sergey.kosov.market.domains.Status
import net.sergey.kosov.market.domains.User
import net.sergey.kosov.market.repository.order.OrderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service

@Service
class MarketOrderService @Autowired constructor(var orderRepository: OrderRepository) : OrderService {
    private val cancelableStatuses = listOf(Status.PROCESSING, Status.CREATED)
    private val completeStatuses = listOf(Status.PROCESSING)
    private val processStatuses = listOf(Status.CREATED)

    override fun create(product: Product, count: Int, customer: User): Order {
        return orderRepository.insert(Order(product = product, count = count, customer = customer))
    }

    override fun findOrder(orderId: String): Order {
        return orderRepository.findOne(orderId)
    }

    override fun processOrder(order: Order): Order {
        if (order.status !in processStatuses) {
            throw IllegalArgumentException("Процессить можно только ордера в статусе ${Status.CREATED}")
        }
        return orderRepository.save(order.changeStatus(Status.PROCESSING))
    }

    override fun processOrder(orderId: String): Order {
        return processOrder(findOrder(orderId))
    }

    override fun completeOrder(order: Order): Order {
        if (order.status !in completeStatuses) {
            throw IllegalArgumentException("Комплитить можно только ордера в статусе ${Status.PROCESSING}")
        }
        return orderRepository.save(order.changeStatus(Status.COMPLETED))
    }

    override fun completeOrder(orderId: String): Order {
        return completeOrder(findOrder(orderId))
    }

    override fun cancelOrder(order: Order): Order {
        if (order.status !in cancelableStatuses) {
            throw IllegalArgumentException("Отменять можно только ордера в статусе ${Status.PROCESSING} или ${Status.CREATED}")
        }
        return orderRepository.save(order.changeStatus(Status.CANCELED))
    }

    override fun cancelOrder(orderId: String): Order {
        return cancelOrder(findOrder(orderId))
    }

    override fun findOrders(customer: User, status: Status?): List<Order> {
        val query = Query()
        return if (status == null) {
            query.addCriteria(Criteria.where("customer").`is`(customer))
            orderRepository.findByQuery(query)
        } else {
            query.addCriteria(Criteria.where("customer").`is`(customer).and("status").`is`(status))
            orderRepository.findByQuery(query)
        }
    }
}