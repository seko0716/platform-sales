package net.sergey.kosov.market.services

import net.sergey.kosov.common.exceptions.NotFoundException
import net.sergey.kosov.market.api.AccountApi
import net.sergey.kosov.market.domains.entity.Order
import net.sergey.kosov.market.domains.entity.Order._Order
import net.sergey.kosov.market.domains.entity.Product
import net.sergey.kosov.market.domains.entity.Status
import net.sergey.kosov.market.domains.entity.Status.*
import net.sergey.kosov.market.domains.view.wrappers.OrderViewCreation
import net.sergey.kosov.market.repository.order.OrderRepository
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class MarketOrderService @Autowired constructor(var orderRepository: OrderRepository,
                                                val productService: ProductService,
                                                val accountApi: AccountApi) : OrderService {
    private val cancelableStatuses = listOf(CREATED)
    private val completeStatuses = listOf(PROCESSING, PROCESSED)
    private val deletableStatuses = listOf(CANCELED, COMPLETED, IN_A_CART)
    private val processingStatuses = listOf(CREATED)
    private val processedStatuses = listOf(PROCESSING)

    override fun createOrderCart(productId: String, name: String): Order {
        val customer = accountApi.getUser(name)
        val orders = orderRepository.findByQuery(Query(Criteria.where(_Order.STATUS).`is`(Status.IN_A_CART))
                .addCriteria(Criteria.where("${_Order.product}.${Product._Product.ID}").`is`(ObjectId(productId)))
                .addCriteria(Criteria.where(_Order.CUSTOMER).`is`(customer)))
        return if (orders.isNotEmpty()) {
            orders.first()
        } else {
            val order = createOrder(name, productId, 1)
            order.status = IN_A_CART
            orderRepository.insert(order)
        }
    }

    override fun updateOrderCart(orderId: String, count: Int, name: String): Order {
        val customer = accountApi.getUser(name)
        val findByQuery = orderRepository.findByQuery(Query(Criteria.where(_Order.STATUS).`is`(Status.IN_A_CART))
                .addCriteria(Criteria.where(_Order.ID).`is`(orderId))
                .addCriteria(Criteria.where(_Order.CUSTOMER).`is`(customer)))
        if (findByQuery.size != 1) {
            throw NotFoundException("Can Not Found Order By id = $orderId")
        }
        val order = findByQuery.first()
        order.count = count
        return orderRepository.save(order)
    }

    override fun create(orderViewCreation: OrderViewCreation, customerName: String): Order {
        val order = createOrder(customerName, orderViewCreation.productId, orderViewCreation.count)
        return orderRepository.insert(order)
    }

    private fun createOrder(customerName: String, productId: String, count: Int): Order {
        val customer = accountApi.getUser(username = customerName)
        val product = productService.findProductById(productId)
        return Order(product = product, count = count, customer = customer)
    }

    override fun getOrders(customerName: String): List<Order> {
        val customer = accountApi.getUser(customerName)
        return orderRepository.findByQuery(getQueryOrder().addCriteria(Criteria.where(_Order.CUSTOMER).`is`(customer)))
    }

    override fun findOrder(orderId: String, name: String): Order {
        val customer = accountApi.getUser(name)
        val account = accountApi.getAccount(name)
        val findByQuery = orderRepository.findByQuery(getQueryOrder()
                .addCriteria(Criteria.where(_Order.ID).`is`(orderId))
                .addCriteria(Criteria().orOperator(
                        Criteria.where(_Order.CUSTOMER).`is`(customer),
                        Criteria.where("${_Order.product}.${Product._Product.ACCOUNT}").`is`(account))))
        if (findByQuery.size != 1) {
            throw NotFoundException("Can Not Found Order By id = $orderId")
        }
        return findByQuery.first()
    }

    override fun processingOrder(orderId: String, name: String): Order {
        val order = findOrderForSaler(name, orderId)
        return orderRepository.save(changeStatus(order, PROCESSING))
    }

    override fun processedOrder(orderId: String, name: String): Order {
        val order = findOrderForSaler(name, orderId)
        return orderRepository.save(changeStatus(order, PROCESSED))
    }

    private fun findOrderForSaler(name: String, orderId: String): Order {
        val account = accountApi.getAccount(name)
        val findByQuery = orderRepository.findByQuery(getQueryOrder()
                .addCriteria(Criteria.where(_Order.ID).`is`(orderId))
                .addCriteria(Criteria.where("${_Order.product}.${Product._Product.ACCOUNT}").`is`(account)))
        if (findByQuery.size != 1) {
            throw NotFoundException("Can Not Found Order By id = $orderId")
        }
        val order = findByQuery.first()
        return order
    }

    override fun completeOrder(orderId: String, name: String): Order {
        val order = findOrderForCustomer(name, orderId)
        return orderRepository.save(changeStatus(order, COMPLETED))
    }

    override fun cancelOrder(orderId: String, name: String): Order {
        val order = findOrderForCustomer(name, orderId)
        return orderRepository.save(changeStatus(order, CANCELED))
    }

    private fun findOrderForCustomer(name: String, orderId: String): Order {
        val customer = accountApi.getUser(name)
        val findByQuery = orderRepository.findByQuery(getQueryOrder()
                .addCriteria(Criteria.where(_Order.ID).`is`(orderId))
                .addCriteria(Criteria.where(_Order.CUSTOMER).`is`(customer)))
        if (findByQuery.size != 1) {
            throw NotFoundException("Can Not Found Order By id = $orderId")
        }
        val order = findByQuery.first()
        return order
    }

    private fun changeStatus(order: Order, status: Status): Order {
        if (order.statusHistory.any { it.first == COMPLETED }) {
            throw IllegalStateException("Нельзя сетить статус после $COMPLETED-- ордер звершен")
        }
        if (order.statusHistory.any { it.first == IN_A_CART }) {
            throw IllegalStateException("Нельзя сетить статус $IN_A_CART")
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
                if (order.status !in processingStatuses) {
                    throw IllegalArgumentException("Процессить можно только ордера в статусе $processingStatuses")
                }
            }
            PROCESSED -> {
                if (order.status !in processedStatuses) {
                    throw IllegalArgumentException("Процессить можно только ордера в статусе $processedStatuses")
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

    override fun deleteOrder(orderId: String, name: String) {
        val customer = accountApi.getUser(name)
        val findByQuery = orderRepository.findByQuery(Query().addCriteria(Criteria.where(_Order.ID).`is`(orderId))
                .addCriteria(Criteria.where(_Order.CUSTOMER).`is`(customer)))
        if (findByQuery.size != 1) {
            throw NotFoundException("Can Not Found Order By id = $orderId")
        }
        val order = findByQuery.first()
        if (order.status in deletableStatuses) {
            orderRepository.delete(order)
        } else {
            throw IllegalArgumentException("Удалять можно только ордера в статусе $deletableStatuses")
        }
    }

    override fun getCart(name: String): List<Order> {
        val customer = accountApi.getUser(name)
        return orderRepository.findByQuery(Query(Criteria.where(_Order.STATUS).`is`(Status.IN_A_CART))
                .addCriteria(Criteria.where(_Order.CUSTOMER).`is`(customer)))
    }

    private fun getQueryOrder(): Query {
        return Query.query(Criteria.where(_Order.STATUS).ne(Status.IN_A_CART))
    }
}