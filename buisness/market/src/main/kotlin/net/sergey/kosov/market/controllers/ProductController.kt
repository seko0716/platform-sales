package net.sergey.kosov.market.controllers

import net.sergey.kosov.market.domains.Filter
import net.sergey.kosov.market.domains.Product
import net.sergey.kosov.market.domains.ProductViewCreation
import net.sergey.kosov.market.services.ProductService
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
class ProductController(val productService: ProductService) {
    @GetMapping("/product/{id}")
    fun findProductById(@PathVariable("id") id: String): Product {
        return productService.findProductById(id)
    }

    @GetMapping("/products/chart")
    fun getProducts4Chart(principal: Principal): List<Product> {
        return productService.getProducts4Chart(principal)
    }

    @GetMapping("/product/{id}/disabled")
    fun disabledProduct(@PathVariable("id") id: String): Product { //todo   валидация того что пользователь принадлежит аккаунту продукта
        return productService.disabledProduct(id)
    }

    @GetMapping("/product/{id}/enabled")
    fun enabledProduct(@PathVariable("id") id: String): Product { //todo   валидация того что пользователь принадлежит аккаунту продукта
        return productService.enabledProduct(id)
    }

    @PutMapping("/product")
    fun createProduct(@RequestBody product: ProductViewCreation): Product {
        return productService.createProduct(product)
    }

    @PostMapping("/products")
    fun findProducts(@RequestBody filter: Filter): List<Product> {
        return productService.findProducts(filter)
    }
}