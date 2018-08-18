package net.sergey.kosov.market.services

import net.sergey.kosov.common.exceptions.NotFoundException
import net.sergey.kosov.market.api.AccountApi
import net.sergey.kosov.market.api.StatisticApi
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
class MarketOrderService @Autowired constructor(private var orderRepository: OrderRepository,
                                                private val productService: ProductService,
                                                private val statisticApi: StatisticApi,
                                                private val accountApi: AccountApi) : OrderService {
    private val cancelableStatuses = listOf(CREATED)
    private val completeStatuses = listOf(PROCESSING, PROCESSED)
    private val deletableStatuses = listOf(CANCELED, COMPLETED, IN_A_CART)
    private val processingStatuses = listOf(CREATED)
    private val processedStatuses = listOf(PROCESSING)

    override fun createOrderCart(productId: String, userName: String): Order {
        val order = orderRepository.findOneByQuery(getQueryCart()
                .addCriteria(getCriteriaProductId(productId))
                .addCriteria(getCriteriaCustomer(userName)))

        order?.let { return order }

        val newOrder = createOrder(userName, productId, 1)
        newOrder.status = IN_A_CART
        return orderRepository.insert(newOrder)
    }

    override fun updateOrderCart(orderId: String, count: Int, userName: String): Order {
        val order = getOrder(orderId, userName)
        order.count = count
        return orderRepository.save(order)
    }

    override fun create(orderViewCreation: OrderViewCreation, customerName: String): Order {
        val order = createOrder(customerName, orderViewCreation.productId, orderViewCreation.count)
        statisticApi.orderAction(order)
        return orderRepository.insert(order)
    }

    private fun createOrder(customerName: String, productId: String, count: Int): Order {
        val customer = accountApi.getUser(username = customerName)
        val product = productService.findProductById(productId)
        return Order(product = product, count = count, customer = customer)
    }

    override fun getOrders(customerName: String): List<Order> {
        return orderRepository.findByQuery(getQueryOrder()
                .addCriteria(getCriteriaCustomer(customerName))
                .with(getSortByCreatedTime()))
    }

    override fun findOrder(orderId: String, userName: String): Order {
        val order = getOrderById(orderId)
        return order?.let {
            val user = accountApi.getUser(userName)
            val marketId = order.product.account.id
            if (order.customer != user && accountApi.getAccount(userName, marketId) != order.product.account) {
                throw NotFoundException("Can Not Found Order By id = $orderId")
            }
            order
        } ?: throw NotFoundException("Can Not Found Order By id = $orderId")
    }

    override fun processingOrder(orderId: String, userName: String): Order {
        val order = findOrderForSeller(userName, orderId)
        val result = orderRepository.save(changeStatus(order, PROCESSING))
        statisticApi.orderAction(result)
        return result
    }

    override fun processedOrder(orderId: String, userName: String): Order {
        val order = findOrderForCustomer(userName, orderId)
        val result = orderRepository.save(changeStatus(order, PROCESSED))
        statisticApi.orderAction(result)
        return result
    }

    private fun findOrderForSeller(userName: String, orderId: String): Order {
        val order = getOrderById(orderId)
        return order?.let {
            val marketId = order.product.account.id
            if (accountApi.getAccount(userName, marketId) != order.product.account) {
                throw NotFoundException("Can Not Found Order By id = $orderId")
            }
            order
        } ?: throw NotFoundException("Can Not Found Order By id = $orderId")
    }

    override fun completeOrder(orderId: String, userName: String): Order {
        val order = findOrderForCustomer(userName, orderId)
        val result = orderRepository.save(changeStatus(order, COMPLETED))
        statisticApi.orderAction(result)
        return result
    }

    override fun cancelOrder(orderId: String, userName: String): Order {
        val order = findOrderForCustomer(userName, orderId)
        val result = orderRepository.save(changeStatus(order, CANCELED))
        statisticApi.orderAction(result)
        return result
    }

    private fun findOrderForCustomer(userName: String, orderId: String): Order {
        return orderRepository.findOneByQuery(getQueryOrder()
                .addCriteria(getCriteriaOrderId(orderId))
                .addCriteria(getCriteriaCustomer(userName)))
                ?: throw NotFoundException("Can Not Found Order By id = $orderId")
    }

    private fun changeStatus(order: Order, status: Status): Order {
        if (order.statusHistory.any { it.status == COMPLETED }) {
            throw IllegalStateException("Нельзя сетить статус после $COMPLETED-- ордер звершен")
        }
        if (order.statusHistory.any { it.status == IN_A_CART }) {
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
            this.statusHistory.add(Order.StatusHistoryItem(status, LocalDateTime.now()))
        }
    }

    override fun buyCart(orderId: String, userName: String): Order {
        val order = getOrder(orderId, userName)
        order.status = CREATED
        return orderRepository.save(order)
    }

    override fun deleteOrder(orderId: String, userName: String) {
        val order = orderRepository.findOneByQuery(Query()
                .addCriteria(getCriteriaOrderId(orderId))
                .addCriteria(getCriteriaCustomer(userName)))
                ?: throw NotFoundException("Can Not Found Order By id = $orderId")

        if (order.status in deletableStatuses) {
            orderRepository.delete(order)
        } else {
            throw IllegalArgumentException("Удалять можно только ордера в статусе $deletableStatuses")
        }
    }

    override fun getCart(userName: String): List<Order> {
        return orderRepository.findByQuery(getQueryCart()
                .addCriteria(getCriteriaCustomer(userName)))
    }

    private fun getCriteriaOrderId(orderId: String) = Criteria.where(_Order.ID).`is`(orderId)

    /** username get from principal*/
    private fun getCriteriaCustomer(customer: String) = Criteria.where("${_Order.CUSTOMER}.email").`is`(customer)

    private fun getQueryCart() = Query(Criteria.where(_Order.STATUS).`is`(IN_A_CART))

    private fun getQueryOrder() = Query.query(Criteria.where(_Order.STATUS).ne(Status.IN_A_CART))

    private fun getSortByCreatedTime() = Sort(_Order.CREATED_TIME)

    private fun getOrderById(orderId: String) =
            orderRepository.findByQuery(getQueryOrder()
                    .addCriteria(getCriteriaOrderId(orderId)))
                    .firstOrNull()

    private fun getCriteriaProductId(productId: String) =
            Criteria.where("${_Order.product}.${Product._Product.ID}").`is`(ObjectId(productId))

    private fun getOrder(orderId: String, customer: String) =
            orderRepository.findOneByQuery(getQueryCart()
                    .addCriteria(getCriteriaOrderId(orderId))
                    .addCriteria(getCriteriaCustomer(customer)))
                    ?: throw NotFoundException("Can Not Found Order By id = $orderId")
}
