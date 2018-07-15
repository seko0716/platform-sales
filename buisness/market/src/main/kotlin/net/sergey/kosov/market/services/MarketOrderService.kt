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
import org.springframework.data.domain.Sort
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

    override fun createOrderCart(productId: String, userName: String): Order {
        val customer = accountApi.getUser(userName)
        val orders = orderRepository.findByQuery(Query(Criteria.where(_Order.STATUS).`is`(Status.IN_A_CART))
                .addCriteria(Criteria.where("${_Order.product}.${Product._Product.ID}").`is`(ObjectId(productId)))
                .addCriteria(Criteria.where(_Order.CUSTOMER).`is`(customer)))
        return if (orders.isNotEmpty()) {
            orders.first()
        } else {
            val order = createOrder(userName, productId, 1)
            order.status = IN_A_CART
            orderRepository.insert(order)
        }
    }

    override fun updateOrderCart(orderId: String, count: Int, userName: String): Order {
        val customer = accountApi.getUser(userName)
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
        return orderRepository.findByQuery(getQueryOrder().addCriteria(Criteria.where(_Order.CUSTOMER).`is`(customer)).with(Sort(_Order.CREATED_TIME)))
    }

    override fun findOrder(orderId: String, userName: String): Order {
        val order = orderRepository.findByQuery(getQueryOrder()
                .addCriteria(Criteria.where(_Order.ID).`is`(orderId))).firstOrNull()
        if (order != null) {
            val user = accountApi.getUser(userName)
            val marketId = order.product.account.id
            if (order.customer != user && accountApi.getAccount(userName, marketId) != order.product.account) {
                throw NotFoundException("Can Not Found Order By id = $orderId")
            }
            return order
        }
        throw NotFoundException("Can Not Found Order By id = $orderId")
    }

    override fun processingOrder(orderId: String, userName: String): Order {
        val order = findOrderForSaler(userName, orderId)
        return orderRepository.save(changeStatus(order, PROCESSING))
    }

    override fun processedOrder(orderId: String, userName: String): Order {
        val order = findOrderForSaler(userName, orderId)
        return orderRepository.save(changeStatus(order, PROCESSED))
    }

    private fun findOrderForSaler(userName: String, orderId: String): Order {
        val order = orderRepository.findByQuery(getQueryOrder()
                .addCriteria(Criteria.where(_Order.ID).`is`(orderId))).firstOrNull()
        if (order != null) {
            val marketId = order.product.account.id
            if (accountApi.getAccount(userName, marketId) != order.product.account) {
                throw NotFoundException("Can Not Found Order By id = $orderId")
            }
            return order
        }
        throw NotFoundException("Can Not Found Order By id = $orderId")
    }

    override fun completeOrder(orderId: String, userName: String): Order {
        val order = findOrderForCustomer(userName, orderId)
        return orderRepository.save(changeStatus(order, COMPLETED))
    }

    override fun cancelOrder(orderId: String, userName: String): Order {
        val order = findOrderForCustomer(userName, orderId)
        return orderRepository.save(changeStatus(order, CANCELED))
    }

    private fun findOrderForCustomer(userName: String, orderId: String): Order {
        val customer = accountApi.getUser(userName)
        val findByQuery = orderRepository.findByQuery(getQueryOrder()
                .addCriteria(Criteria.where(_Order.ID).`is`(orderId))
                .addCriteria(Criteria.where(_Order.CUSTOMER).`is`(customer)))
        if (findByQuery.size != 1) {
            throw NotFoundException("Can Not Found Order By id = $orderId")
        }
        return findByQuery.first()
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

            else -> {
                throw IllegalArgumentException("status not supported")
            }
        }

        return order.apply {
            this.status = status
            this.statusHistory.add(Pair(status, LocalDateTime.now()))
        }
    }

    override fun buyCart(orderId: String, userName: String): Order {
        val customer = accountApi.getUser(userName)
        val findByQuery = orderRepository.findByQuery(Query(Criteria.where(_Order.STATUS).`is`(Status.IN_A_CART))
                .addCriteria(Criteria.where(_Order.ID).`is`(orderId))
                .addCriteria(Criteria.where(_Order.CUSTOMER).`is`(customer)))
        if (findByQuery.size != 1) {
            throw NotFoundException("Can Not Found Order By id = $orderId")
        }
        val order = findByQuery.first()
        order.status = CREATED
        return orderRepository.save(order)
    }

    override fun deleteOrder(orderId: String, userName: String) {
        val customer = accountApi.getUser(userName)
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

    override fun getCart(userName: String): List<Order> {
        val customer = accountApi.getUser(userName)
        return orderRepository.findByQuery(Query(Criteria.where(_Order.STATUS).`is`(Status.IN_A_CART))
                .addCriteria(Criteria.where(_Order.CUSTOMER).`is`(customer)))
    }

    private fun getQueryOrder(): Query {
        return Query.query(Criteria.where(_Order.STATUS).ne(Status.IN_A_CART))
    }
}