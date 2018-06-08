package net.sergey.kosov.market.services

import net.sergey.kosov.market.api.StatisticApi
import net.sergey.kosov.market.domains.Filter
import net.sergey.kosov.market.domains.Product
import net.sergey.kosov.market.repository.product.ProductRepository

import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.security.Principal

@Service
class MarketProductService(private val productRepository: ProductRepository,
                           private val statisticApi: StatisticApi,
                           private val categoryService: CategoryService,
                           @Value("\${chart.size}") private var chartSize: Int) : ProductService {

    override fun createProduct(title: String, description: String, categoryId: String): Product {
        val accountId = getCurrentAccount()
        val category = categoryService.findCategoryById(categoryId)
        val products = Product(title = title, description = description, accountId = accountId, price = BigDecimal.ZERO,
                category = category)
        return productRepository.save(products) ?: throw IllegalStateException("не смог сохранить")
    }

    private fun getCurrentAccount() = ObjectId()

    override fun findProductById(id: String): Product {
        return productRepository.findOne(id) ?: throw ChangeSetPersister.NotFoundException()
    }

    override fun getProducts4Chart(principal: Principal): List<Product> {
        val productsIdsChart = statisticApi.getChart(username = principal.name, chartSize = chartSize)
        return productRepository.findAll(productsIdsChart).toList()
    }

    override fun disabledProduct(product: Product): Product = productRepository.save(product.apply { product.enabled = false })

    override fun enabledProduct(product: Product): Product = productRepository.save(product.apply { product.enabled = true })

    override fun findProducts(filter: Filter): List<Product> {
        val query = Query()
                .addCriteria(Criteria.where("price").gte(filter.priceLeft))
                .addCriteria(Criteria.where("price").lte(filter.priceRight))
                .addCriteria(Criteria.where("title").regex(filter.title))
        return productRepository.findByQuery(query)
    }
}