package net.sergey.kosov.market

import feign.RequestInterceptor
import net.sergey.kosov.market.domains.entity.Characteristic
import net.sergey.kosov.market.domains.view.wrappers.CategoryViewCreation
import net.sergey.kosov.market.domains.view.wrappers.OrderViewCreation
import net.sergey.kosov.market.domains.view.wrappers.ProductViewCreation
import net.sergey.kosov.market.services.CategoryService
import net.sergey.kosov.market.services.OrderService
import net.sergey.kosov.market.services.ProductService
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import java.math.BigDecimal
import javax.annotation.PostConstruct


@EnableOAuth2Client
@EnableResourceServer
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
                orderService.create(OrderViewCreation(id), "admin")
            }
            if (it % 5 == 0) {
                val order = orderService.create(OrderViewCreation(id), "admin")
                orderService.processingOrder(orderId = order.id.toString(), name = "test")
            }
            if (it % 4 == 0) {
                val order = orderService.create(OrderViewCreation(id), "admin")
                orderService.processingOrder(orderId = order.id.toString(), name = "test")
                orderService.processedOrder(orderId = order.id.toString(), name = "test")
            }
            if (it % 7 == 0) {
                val order = orderService.create(OrderViewCreation(id), "admin")
                orderService.processingOrder(orderId = order.id.toString(), name = "test")
                orderService.completeOrder(orderId = order.id.toString(), name = "admin")
            }
            if (it % 9 == 0) {
                val order = orderService.create(OrderViewCreation(id), "admin")
//                orderService.processingOrder(orderId = order.id.toString(), name = "test")
//                orderService.completeOrder(orderId = order.id.toString())
                orderService.cancelOrder(orderId = order.id.toString(), name = "admin")
            }
        }

    }
}

fun main(args: Array<String>) {
    SpringApplication.run(MarketApplication::class.java, *args)
}

@Configuration
class CustomResourceServerConfigurerAdapter : ResourceServerConfigurerAdapter() {

    @Bean
    @ConfigurationProperties(prefix = "security.oauth2.client")
    fun clientCredentialsResourceDetails(): ClientCredentialsResourceDetails {
        return ClientCredentialsResourceDetails()
    }

    @Bean
    fun oauth2FeignRequestInterceptor(): RequestInterceptor {
        return OAuth2FeignRequestInterceptor(DefaultOAuth2ClientContext(), clientCredentialsResourceDetails())
    }

    @Bean
    fun clientCredentialsRestTemplate(): OAuth2RestTemplate {
        return OAuth2RestTemplate(clientCredentialsResourceDetails())
    }


    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
                .antMatchers("/products").permitAll()
                .anyRequest().authenticated()
    }
}