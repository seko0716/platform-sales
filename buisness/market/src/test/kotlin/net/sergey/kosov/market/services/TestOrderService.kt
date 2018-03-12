package net.sergey.kosov.market.services

import net.sergey.kosov.market.api.StatisticApi
import net.sergey.kosov.market.configuration.ConfigurationFeign
import net.sergey.kosov.market.domains.Order
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

@RunWith(SpringRunner::class)
@ContextHierarchy(ContextConfiguration(classes = [GoodsServiceConfig::class, ConfigurationFeign::class]))
class TestOrderService {
    @Autowired
    lateinit var orderService: OrderService
    @Autowired
    lateinit var goodsService: GoodsService
    @MockBean
    private lateinit var statisticService: StatisticApi

    var orderId = ObjectId()

    @Before
    fun before() {
        val goods = goodsService.createGoods(title = "name!!", description = "description!!")
        var order: Order = orderService.create(goods = goods, count = 2, customer = User("1", "2"))
        orderId = order.id
    }

    @Test
    fun createNewOrder() {
        val goods = goodsService.createGoods(title = "name", description = "description")
        var currentUser = User("2", "3")
        var order: Order = orderService.create(goods = goods, count = 2, customer = currentUser)
        Assert.assertEquals(order, orderService.findOrder(orderId))
        Assert.assertEquals(Status.CREATED, order.status)
    }

    @Test
    fun processOrder() {//после оплаты
        var order: Order = orderService.findOrder(orderId = orderId)
        val processedOrder = orderService.processOrder(order = order)
        Assert.assertEquals(Status.PROCESSING, processedOrder.status)
    }

    @Test
    fun completeOrder() {//после оплаты
        var order: Order = orderService.findOrder(orderId = orderId)
        val completedOrder = orderService.completeOrder(order = order)
        Assert.assertEquals(Status.COMPLETED, completedOrder.status)
    }

    @Test
    fun cancelOrder() {//после оплаты
        var order: Order = orderService.findOrder(orderId = orderId)
        var canceledOrder: Order = orderService.cancelOrder(order = order)
        Assert.assertEquals(Status.CANCELED, canceledOrder.status)
    }

    @Test
    fun getOrders() {
        val ordersExp = (0..10).mapTo(ArrayList()) {
            val goods = goodsService.createGoods(title = "$it", description = "description!!$it")
            orderService.create(goods = goods, count = 2, customer = User("1", "2"))
        }

        var currentUser = User("2", "3")
        var orders: List<Order> = orderService.findOrders(customer = currentUser, status = Status.CREATED)
        Assert.assertEquals(ordersExp, orders)
    }
}