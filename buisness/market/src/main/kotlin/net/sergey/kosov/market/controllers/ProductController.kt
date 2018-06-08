package net.sergey.kosov.market.controllers

import net.sergey.kosov.market.domains.Filter
import net.sergey.kosov.market.domains.Product
import net.sergey.kosov.market.services.ProductService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController
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
    fun createProduct(title: String, description: String, categoryId: String): Product {
        return productService.createProduct(title, description, categoryId)
    }

    @GetMapping("/products")
    fun findProducts(filter: Filter): List<Product> {
        return productService.findProducts(filter)
    }
}