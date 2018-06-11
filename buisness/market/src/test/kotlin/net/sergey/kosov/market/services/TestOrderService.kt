package net.sergey.kosov.market.services

import net.sergey.kosov.market.api.StatisticApi
import net.sergey.kosov.market.configuration.ConfigurationFeign
import net.sergey.kosov.market.domains.Order
import net.sergey.kosov.market.domains.ProductViewCreation
import net.sergey.kosov.market.domains.Status
import net.sergey.kosov.market.domains.User
import org.bson.types.ObjectId
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.ContextHierarchy
import org.springframework.test.context.junit4.SpringRunner
import java.math.BigDecimal

@RunWith(SpringRunner::class)
@ContextHierarchy(ContextConfiguration(classes = [GoodsServiceConfig::class, ConfigurationFeign::class]))
class TestOrderService {
    @Autowired
    lateinit var orderService: OrderService
    @Autowired
    lateinit var productService: ProductService
    @MockBean
    private lateinit var statisticService: StatisticApi

    var orderId = ObjectId().toString()

    @Before
    fun before() {
        val goods = productService.createProduct(ProductViewCreation(title = "name!!", description = "description!!", categoryId = "", price = BigDecimal.ZERO))
        val order: Order = orderService.create(product = goods, count = 2, customer = User("1", "2"))
        orderId = order.id.toString()
    }

    @Test
    fun createNewOrder() {
        val goods = productService.createProduct(ProductViewCreation(title = "name!!", description = "description!!", categoryId = "", price = BigDecimal.ZERO))
        val currentUser = User("2", "3")
        val order: Order = orderService.create(product = goods, count = 2, customer = currentUser)
        Assert.assertEquals(order, orderService.findOrder(order.id.toString()))
        Assert.assertEquals(Status.CREATED, order.status)
    }

    @Test
    fun processOrder() {//после оплаты
        val order: Order = orderService.findOrder(orderId = orderId)
        val processedOrder = orderService.processOrder(order = order)
        Assert.assertEquals(Status.PROCESSING, processedOrder.status)
    }

    @Test
    fun completeOrder() {//после оплаты
        val order: Order = orderService.findOrder(orderId = orderId)
        val processedOrder = orderService.processOrder(order = order)
        val completedOrder = orderService.completeOrder(order = processedOrder)
        Assert.assertEquals(Status.COMPLETED, completedOrder.status)
    }

    @Test
    fun cancelOrder() {//после оплаты
        val order: Order = orderService.findOrder(orderId = orderId)
        val canceledOrder: Order = orderService.cancelOrder(order = order)
        Assert.assertEquals(Status.CANCELED, canceledOrder.status)
    }

    @Test
    fun getOrders() {
        val currentUser = User("22", "3")
        val ordersExp = (0..10).mapTo(ArrayList()) {
            val goods = productService.createProduct(ProductViewCreation(title = "name!!$it", description = "description!!$it", categoryId = "", price = BigDecimal.ZERO))
            orderService.create(product = goods, count = 2, customer = currentUser)
        }

        val orders: List<Order> = orderService.findOrders(customer = currentUser, status = Status.CREATED)
        Assert.assertEquals(ordersExp.size, orders.size)
        Assert.assertEquals(ordersExp, orders)
    }
}