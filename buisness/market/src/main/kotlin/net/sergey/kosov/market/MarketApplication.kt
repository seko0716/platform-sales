package net.sergey.kosov.market

import net.sergey.kosov.market.domains.view.wrappers.CategoryViewCreation
import net.sergey.kosov.market.domains.view.wrappers.ProductViewCreation
import net.sergey.kosov.market.services.CategoryService
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
                        var categoryService: CategoryService) {
    @PostConstruct
    fun init() {
        val create = categoryService.create(CategoryViewCreation(title = "category"), "test")

        (1..200).forEach {
            val id = productService.createProduct(
                    ProductViewCreation(title = "name!!$it", description = "description!!", categoryId = create.id.toString(), price = BigDecimal.ZERO),
                    "test").id.toString()
            productService.enabledProduct(id)
        }

    }
}

fun main(args: Array<String>) {
    SpringApplication.run(MarketApplication::class.java, *args)
}