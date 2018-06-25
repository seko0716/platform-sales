package net.sergey.kosov.market

import net.sergey.kosov.market.domains.entity.Characteristic
import net.sergey.kosov.market.domains.view.wrappers.CategoryViewCreation
import net.sergey.kosov.market.domains.view.wrappers.OrderViewCreation
import net.sergey.kosov.market.domains.view.wrappers.ProductViewCreation
import net.sergey.kosov.market.services.CategoryService
import net.sergey.kosov.market.services.OrderService
import net.sergey.kosov.market.services.ProductService
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client
import java.math.BigDecimal
import javax.annotation.PostConstruct


@EnableOAuth2Client
//@EnableResourceServer //todo revert for oAuth. comment for base64
@SpringBootApplication
class MarketApplication(var productService: ProductService,
                        var categoryService: CategoryService,
                        var orderService: OrderService) {
    @PostConstruct
    fun init() {
        val categoryViewCreation = CategoryViewCreation(title = "category",
                characteristics = listOf(Characteristic(name = "cpu"),
                        Characteristic(name = "gpu"),
                        Characteristic(name = "ram"),
                        Characteristic(name = "rom"),
                        Characteristic(name = "box"),
                        Characteristic(name = "fan"),
                        Characteristic(name = "ports")
                ))
        val create = categoryService.create(categoryViewCreation, "test")
        categoryViewCreation.parentId = create.id.toString()
        val create2 = categoryService.create(categoryViewCreation, "test")

        (1..200).forEach {
            val id = productService.createProduct(
                    ProductViewCreation(title = "Corsair GS600 600 Watt PSU$it",
                            description = "The Corsair Gaming Series GS600 is the ideal price/performance choice for mid-spec gaming PC",
                            categoryId = create.id.toString(),
                            productInfo = "The Corsair Gaming Series GS600 power supply is the ideal price-performance solution for building or upgrading a Gaming PC. A single +12V rail provides up to 48A of reliable, continuous power for multi-core gaming PCs with multiple graphics cards. The ultra-quiet, dual ball-bearing fan automatically adjusts its speed according to temperature, so it will never intrude on your music and games. Blue LEDs bathe the transparent fan blades in a cool glow. Not feeling blue? You can turn off the lighting with the press of a button.",
                            price = BigDecimal.valueOf(1234.00 + it)),
                    "test").id.toString()
            productService.enabledProduct(id)
            if (it % 10 == 0) {
                orderService.create(OrderViewCreation(id), "user")
            }
            if (it % 5 == 0) {
                val order = orderService.create(OrderViewCreation(id), "user")
                orderService.processingOrder(orderId = order.id.toString(), name = "test")
            }
            if (it % 7 == 0) {
                val order = orderService.create(OrderViewCreation(id), "user")
                orderService.processingOrder(orderId = order.id.toString(), name = "test")
                orderService.completeOrder(orderId = order.id.toString(), name = "user")
            }
            if (it % 9 == 0) {
                val order = orderService.create(OrderViewCreation(id), "user")
//                orderService.processingOrder(orderId = order.id.toString(), name = "test")
//                orderService.completeOrder(orderId = order.id.toString())
                orderService.cancelOrder(orderId = order.id.toString(), name = "user")
            }
        }

    }
}

fun main(args: Array<String>) {
    SpringApplication.run(MarketApplication::class.java, *args)
}