package net.sergey.kosov.market.services

import net.sergey.kosov.market.configuration.ConfigurationFeign
import net.sergey.kosov.market.domains.Order
import net.sergey.kosov.market.domains.User
import org.bson.types.ObjectId
import org.junit.Assert
import org.junit.Before
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
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

    var orderId = ObjectId()

    @Before
    fun before(): Unit {
        val goods = goodsService.createGoods(title = "name!!", description = "description!!")
        var order: Order = orderService.create(goods = goods, count = 2, customer = User("1", "2"))
        orderId = order.id
    }

    fun createNewOrder() {
        val goods = goodsService.createGoods(title = "name", description = "description")
        var currentUser: User = User("2", "3")
        var order: Order = orderService.create(goods = goods, count = 2, customer = currentUser)
        Assert.assertEquals(order, orderService.findOrder(orderId))
    }

    fun processOrder() {//после оплаты
        var order: Order = orderService.findOrder(orderId = orderId)
        orderService.processOrder(order = order)
    }

    fun completeOrder() {//после оплаты
        var order: Order = orderService.findOrder(orderId = orderId)
        orderService.completeOrder(order = order)
    }

    fun cancelOrder() {//после оплаты
        var order: Order = orderService.findOrder(orderId = orderId)
        orderService.cancelOrder(order = order)
    }




}