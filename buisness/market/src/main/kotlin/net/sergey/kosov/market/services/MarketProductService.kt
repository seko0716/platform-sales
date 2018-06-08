package net.sergey.kosov.market.services

import net.sergey.kosov.market.api.StatisticApi
import net.sergey.kosov.market.domains.Category
import net.sergey.kosov.market.domains.Product
import net.sergey.kosov.market.repository.product.ProductRepository
import net.sergey.kosov.market.utils.toObjectId
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.security.Principal

@Service
class MarketProductService(private val productRepository: ProductRepository,
                           private val statisticApi: StatisticApi,
                           @Value("\${chart.size}") private var chartSize: Int) : ProductService {

    override fun createProduct(title: String, description: String): Product {
        val products = Product(title = title, description = description, accountId = getCurrentAccount(), price = BigDecimal.ZERO,
                category = Category(title = "testCategory"))
        return productRepository.save(products) ?: throw IllegalStateException("не смог сохранить")
    }

    private fun getCurrentAccount() = ObjectId()

    override fun findProductById(id: ObjectId): Product {
        return productRepository.findOne(id) ?: throw ChangeSetPersister.NotFoundException()
    }

    override fun getProducts4Chart(principal: Principal): List<Product> {
        val productsIdsChart = statisticApi.getChart(username = principal.name, chartSize = chartSize)
        return productRepository.findAll(productsIdsChart.map { it.toObjectId() }).toList()
    }

    override fun disabledProduct(productId: ObjectId): Product = disabledProduct(findProductById(productId))

    override fun enabledProduct(productId: ObjectId): Product = enabledProduct(findProductById(productId))

    override fun disabledProduct(product: Product): Product = productRepository.save(product.apply { product.enabled = false })

    override fun enabledProduct(product: Product): Product = productRepository.save(product.apply { product.enabled = true })
}