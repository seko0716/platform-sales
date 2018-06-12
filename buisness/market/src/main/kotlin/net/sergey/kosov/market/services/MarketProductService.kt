package net.sergey.kosov.market.services

import net.sergey.kosov.common.exceptions.NotFoundException
import net.sergey.kosov.market.api.StatisticApi
import net.sergey.kosov.market.domains.entity.Characteristic
import net.sergey.kosov.market.domains.entity.Product
import net.sergey.kosov.market.domains.view.wrappers.ProductFilter
import net.sergey.kosov.market.domains.view.wrappers.ProductViewCreation
import net.sergey.kosov.market.repository.product.ProductRepository
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import java.security.Principal

@Service
class MarketProductService(private val productRepository: ProductRepository,
                           private val statisticApi: StatisticApi,
                           private val categoryService: CategoryService,
                           @Value("\${chart.size}") private var chartSize: Int) : ProductService {

    override fun createProduct(productViewCreation: ProductViewCreation): Product {
        val accountId = getCurrentAccount()
        val category = categoryService.findCategoryById(productViewCreation.categoryId)
        val products = Product(title = productViewCreation.title,
                description = productViewCreation.description,
                accountId = accountId,
                price = productViewCreation.price,
                category = category)
        return productRepository.save(products)
    }

    override fun setCharacteristic(characteristics: List<Characteristic>, id: String): Product {
        val product = findProductById(id)
        product.characteristic = characteristics
        return productRepository.save(product)
    }

    private fun getCurrentAccount() = ObjectId()

    override fun findProductById(id: String): Product {
        return productRepository.findOne(id) ?: throw NotFoundException("can not found product by id $id")
    }

    override fun getProducts4Chart(principal: Principal): List<Product> {
        val productsIdsChart = statisticApi.getChart(username = principal.name, chartSize = chartSize)
        return productRepository.findAll(productsIdsChart).toList()
    }

    override fun disabledProduct(id: String): Product = productRepository.save(findProductById(id).apply { enabled = false })

    override fun enabledProduct(id: String): Product = productRepository.save(findProductById(id).apply { enabled = true })

    override fun findProducts(filter: ProductFilter): List<Product> {
        val betweenCriteria = Criteria.where("price").gte(filter.priceLeft)
                .andOperator(Criteria.where("price").lte(filter.priceRight))
        val query = Query()
                .addCriteria(betweenCriteria)
                .addCriteria(Criteria.where("title").regex(filter.title))
        return productRepository.findByQuery(query)
    }
}