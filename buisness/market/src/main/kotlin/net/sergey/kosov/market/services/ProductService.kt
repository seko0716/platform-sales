package net.sergey.kosov.market.services

import net.sergey.kosov.market.domains.Filter
import net.sergey.kosov.market.domains.Product
import net.sergey.kosov.market.domains.ProductViewCreation
import java.security.Principal

interface ProductService {
    fun findProductById(id: String): Product
    fun getProducts4Chart(principal: Principal): List<Product>
    fun disabledProduct(id: String): Product
    fun enabledProduct(id: String): Product
    fun createProduct(productViewCreation: ProductViewCreation): Product
    fun findProducts(filter: Filter): List<Product>
}