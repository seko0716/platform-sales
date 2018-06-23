package net.sergey.kosov.market.services

import net.sergey.kosov.market.api.AccountApi
import net.sergey.kosov.market.configuration.ConfigurationFeign
import net.sergey.kosov.market.domains.entity.*
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
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.ContextHierarchy
import org.springframework.test.context.junit4.SpringRunner
import java.math.BigDecimal

@RunWith(SpringRunner::class)
@ActiveProfiles("test")
@ContextHierarchy(ContextConfiguration(classes = [ServiceConfig::class, ConfigurationFeign::class]))
class TestOrderService {
    @Autowired
    private
    lateinit var orderService: OrderService
    @Autowired
    private
    lateinit var productService: ProductService
    @MockBean
    private lateinit var accountApi: AccountApi
    @MockBean
    private lateinit var categoryService: CategoryService

    private var orderId = ObjectId().toString()

    @Before
    fun before() {
        Mockito.doReturn(User("22", "3")).`when`(accountApi).getUser("22")
        Mockito.doReturn(User("2", "3")).`when`(accountApi).getUser("2")
        Mockito.doReturn(User("1", "1")).`when`(accountApi).getUser("1")

        Mockito.doReturn(Account("1", "1")).`when`(accountApi).getAccount("1")
        Mockito.doReturn(Account("1", "1")).`when`(accountApi).getAccount("2")
        Mockito.doReturn(Account("1", "1")).`when`(accountApi).getAccount("22")

        Mockito.doReturn(Category(title = "1")).`when`(categoryService).findCategoryById("", "1")
        Mockito.doReturn(Category(title = "1")).`when`(categoryService).findCategoryById("", "2")
        Mockito.doReturn(Category(title = "1")).`when`(categoryService).findCategoryById("", "22")


        val productViewCreation = ProductViewCreation(title = "name!!", description = "description!!", price = BigDecimal.ZERO, categoryId = "", productInfo = "The Corsair Gaming Series GS600 power supply is the ideal price-performance solution for building or upgrading a Gaming PC. A single +12V rail provides up to 48A of reliable, continuous power for multi-core gaming PCs with multiple graphics cards. The ultra-quiet, dual ball-bearing fan automatically adjusts its speed according to temperature, so it will never intrude on your music and games. Blue LEDs bathe the transparent fan blades in a cool glow. Not feeling blue? You can turn off the lighting with the press of a button.")
        val goods = productService.createProduct(productViewCreation, "1")
        val order: Order = orderService.create(OrderViewCreation(productId = goods.id.toString(), count = 2), customerName = "1")
        orderId = order.id.toString()

    }

    @Test
    fun createNewOrder() {
        val productViewCreation = ProductViewCreation(title = "name!!", description = "description!!", price = BigDecimal.ZERO, categoryId = "", productInfo = "The Corsair Gaming Series GS600 power supply is the ideal price-performance solution for building or upgrading a Gaming PC. A single +12V rail provides up to 48A of reliable, continuous power for multi-core gaming PCs with multiple graphics cards. The ultra-quiet, dual ball-bearing fan automatically adjusts its speed according to temperature, so it will never intrude on your music and games. Blue LEDs bathe the transparent fan blades in a cool glow. Not feeling blue? You can turn off the lighting with the press of a button.")
        val currentUser = User("2", "3")
        val goods = productService.createProduct(productViewCreation, currentUser.name)
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
            val productViewCreation = ProductViewCreation(title = "name!!$it", description = "description!!$it", price = BigDecimal.ZERO, categoryId = "", productInfo = "The Corsair Gaming Series GS600 power supply is the ideal price-performance solution for building or upgrading a Gaming PC. A single +12V rail provides up to 48A of reliable, continuous power for multi-core gaming PCs with multiple graphics cards. The ultra-quiet, dual ball-bearing fan automatically adjusts its speed according to temperature, so it will never intrude on your music and games. Blue LEDs bathe the transparent fan blades in a cool glow. Not feeling blue? You can turn off the lighting with the press of a button.")
            val goods = productService.createProduct(productViewCreation, currentUser.name)
            orderService.create(OrderViewCreation(productId = goods.id.toString(), count = 2), customerName = currentUser.name)
        }

        val orders: List<Order> = orderService.findOrders(OrderFilter(customerName = currentUser.name, status = Status.CREATED))
        Assert.assertEquals(ordersExp.size, orders.size)
        Assert.assertEquals(ordersExp, orders)
    }
}