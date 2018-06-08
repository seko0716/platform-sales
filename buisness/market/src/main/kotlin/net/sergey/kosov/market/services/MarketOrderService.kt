package net.sergey.kosov.market.services

import net.sergey.kosov.market.domains.Order
import net.sergey.kosov.market.domains.Product
import net.sergey.kosov.market.domains.Status
import net.sergey.kosov.market.domains.User
import net.sergey.kosov.market.repository.order.OrderRepository
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service

@Service
class MarketOrderService @Autowired constructor(var orderRepository: OrderRepository) : OrderService {

    override fun create(product: Product, count: Int, customer: User): Order {
        return orderRepository.insert(Order(product = product, count = count, customer = customer))
    }

    override fun findOrder(orderId: ObjectId): Order {
        return orderRepository.findOne(orderId)
    }

    override fun processOrder(order: Order): Order {
        if (order.status != Status.CREATED) {
            throw IllegalArgumentException("Процессить можно только ордера в статусе ${Status.CREATED}")
        }
        return orderRepository.save(order.changeStatus(Status.PROCESSING))
    }

    override fun processOrder(orderId: ObjectId): Order {
        return processOrder(findOrder(orderId))
    }

    override fun completeOrder(order: Order): Order {
        if (order.status != Status.PROCESSING) {
            throw IllegalArgumentException("Комплитить можно только ордера в статусе ${Status.PROCESSING}")
        }
        return orderRepository.save(order.changeStatus(Status.COMPLETED))
    }

    override fun completeOrder(orderId: ObjectId): Order {
        return completeOrder(findOrder(orderId))
    }

    override fun cancelOrder(order: Order): Order {
        if (order.status != Status.PROCESSING && order.status != Status.CREATED) {
            throw IllegalArgumentException("Отменять можно только ордера в статусе ${Status.PROCESSING} или ${Status.CREATED}")
        }
        return orderRepository.save(order.changeStatus(Status.CANCELED))
    }

    override fun cancelOrder(orderId: ObjectId): Order {
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