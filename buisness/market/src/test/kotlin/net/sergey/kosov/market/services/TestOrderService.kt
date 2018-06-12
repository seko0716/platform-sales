package net.sergey.kosov.market.services

import net.sergey.kosov.market.api.AccountApi
import net.sergey.kosov.market.configuration.ConfigurationFeign
import net.sergey.kosov.market.domains.entity.Order
import net.sergey.kosov.market.domains.entity.Status
import net.sergey.kosov.market.domains.entity.User
import net.sergey.kosov.market.domains.view.wrappers.OrderFilter
import net.sergey.kosov.market.domains.view.wrappers.OrderViewCreation
import net.sergey.kosov.market.domains.view.wrappers.ProductViewCreation
import org.bson.types.ObjectId
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.ContextHierarchy
import org.springframework.test.context.junit4.SpringRunner
import java.math.BigDecimal

@RunWith(SpringRunner::class)
@ContextHierarchy(ContextConfiguration(classes = [ServiceConfig::class, ConfigurationFeign::class]))
class TestOrderService {
    @Autowired
    lateinit var orderService: OrderService
    @Autowired
    lateinit var productService: ProductService
    @MockBean
    private lateinit var accountApi: AccountApi

    var orderId = ObjectId().toString()

    @Before
    fun before() {
        Mockito.doReturn(User("22", "3")).`when`(accountApi).getUser("22")
        Mockito.doReturn(User("2", "3")).`when`(accountApi).getUser("2")
        Mockito.doReturn(User("1", "1")).`when`(accountApi).getUser("1")

        val goods = productService.createProduct(ProductViewCreation(title = "name!!", description = "description!!", categoryId = "", price = BigDecimal.ZERO))
        val order: Order = orderService.create(OrderViewCreation(productId = goods.id.toString(), count = 2), customerName = "1")
        orderId = order.id.toString()

    }

    @Test
    fun createNewOrder() {
        val goods = productService.createProduct(ProductViewCreation(title = "name!!", description = "description!!", categoryId = "", price = BigDecimal.ZERO))
        val currentUser = User("2", "3")
        val order: Order = orderService.create(OrderViewCreation(productId = goods.id.toString(), count = 2), customerName = currentUser.name)
        Assert.assertEquals(order, orderService.findOrder(order.id.toString()))
        Assert.assertEquals(Status.CREATED, order.status)
    }

    @Test
    fun processOrder() {//после оплаты
        val order: Order = orderService.findOrder(orderId = orderId)
        val processedOrder = orderService.processOrder(orderId = order.id.toString())
        Assert.assertEquals(Status.PROCESSING, processedOrder.status)
    }

    @Test
    fun completeOrder() {//после оплаты
        val order: Order = orderService.findOrder(orderId = orderId)
        val processedOrder = orderService.processOrder(orderId = order.id.toString())
        val completedOrder = orderService.completeOrder(orderId = processedOrder.id.toString())
        Assert.assertEquals(Status.COMPLETED, completedOrder.status)
    }

    @Test
    fun cancelOrder() {//после оплаты
        val order: Order = orderService.findOrder(orderId = orderId)
        val canceledOrder: Order = orderService.cancelOrder(orderId = order.id.toString())
        Assert.assertEquals(Status.CANCELED, canceledOrder.status)
    }

    @Test
    fun getOrders() {
        val currentUser = User("22", "3")
        val ordersExp = (0..10).mapTo(ArrayList()) {
            val goods = productService.createProduct(ProductViewCreation(title = "name!!$it", description = "description!!$it", categoryId = "", price = BigDecimal.ZERO))
            orderService.create(OrderViewCreation(productId = goods.id.toString(), count = 2), customerName = currentUser.name)
        }

        val orders: List<Order> = orderService.findOrders(OrderFilter(customerName = currentUser.name, status = Status.CREATED))
        Assert.assertEquals(ordersExp.size, orders.size)
        Assert.assertEquals(ordersExp, orders)
    }
}