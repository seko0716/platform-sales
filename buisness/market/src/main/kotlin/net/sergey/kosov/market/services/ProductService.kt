package net.sergey.kosov.market.services

import net.sergey.kosov.market.domains.Filter
import net.sergey.kosov.market.domains.Product
import java.security.Principal

interface ProductService {
    fun findProductById(id: String): Product
    fun getProducts4Chart(principal: Principal): List<Product>
    fun disabledProduct(product: Product): Product
    fun enabledProduct(product: Product): Product
    fun createProduct(title: String, description: String, categoryId: String = ""): Product
    fun findProducts(filter: Filter): List<Product>
}