package net.sergey.kosov.market.services

import net.sergey.kosov.market.domains.Product
import org.bson.types.ObjectId
import java.security.Principal

interface ProductService {
    fun findProductById(id: ObjectId): Product
    fun getProducts4Chart(principal: Principal): List<Product>
    fun disabledProduct(productId: ObjectId): Product
    fun disabledProduct(product: Product): Product
    fun enabledProduct(productId: ObjectId): Product
    fun enabledProduct(product: Product): Product
    fun createProduct(title: String, description: String): Product
}