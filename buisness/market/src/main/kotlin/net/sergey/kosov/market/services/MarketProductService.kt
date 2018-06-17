package net.sergey.kosov.market.services

import net.sergey.kosov.common.exceptions.NotFoundException
import net.sergey.kosov.market.api.AccountApi
import net.sergey.kosov.market.api.StatisticApi
import net.sergey.kosov.market.domains.entity.Category
import net.sergey.kosov.market.domains.entity.Characteristic
import net.sergey.kosov.market.domains.entity.Product
import net.sergey.kosov.market.domains.view.wrappers.ProductFilter
import net.sergey.kosov.market.domains.view.wrappers.ProductViewCreation
import net.sergey.kosov.market.repository.product.ProductRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service

@Service
class MarketProductService(private val productRepository: ProductRepository,
                           private val statisticApi: StatisticApi,
                           private val accountApi: AccountApi,
                           private val categoryService: CategoryService,
                           @Value("\${chart.size}") private var chartSize: Int) : ProductService {

    override fun createProduct(productViewCreation: ProductViewCreation, name: String): Product {
        val accountId = getCurrentAccount(name)
        val category = categoryService.findCategoryById(productViewCreation.categoryId, name)
        val tags = calculateTags(productViewCreation, category)

        val products = Product(title = productViewCreation.title,
                description = productViewCreation.description,
                account = accountId,
                price = productViewCreation.price,
                category = category,
                tags = tags)
        return productRepository.save(products)
    }

    private fun calculateTags(productViewCreation: ProductViewCreation, category: Category) =
            productViewCreation.title.split(" +").union(category.characteristics.map { it.name }).toList() + category.title

    override fun setCharacteristic(characteristics: List<Characteristic>, id: String): Product {
        val product = findProductById(id)
        val characteristicNames = getCharacteristicNamesInHierarchy(product.category)
        if (!characteristicNames.containsAll(characteristics.map { it.name })) {
            throw IllegalArgumentException("Содержится недопустимая харектеристика товара для этой категории. допустимые характеристики: $characteristicNames")
        }

        product.characteristic = characteristics
        return productRepository.save(product)
    }

    private fun getCharacteristicNamesInHierarchy(category: Category): List<String> {
        return category.flatMap { it.characteristics.map { it.name } }
    }

    private fun getCurrentAccount(name: String) = accountApi.getAccount(name)

    override fun findProductById(id: String): Product {
        return productRepository.findOne(id) ?: throw NotFoundException("can not found product by id $id")
    }

    override fun getProducts4Chart(name: String): List<Product> {
        val productsIdsChart = statisticApi.getChart(username = name, chartSize = chartSize)
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

    override fun findProducts(): List<Product> {
        val query = Query().limit(100)
        return productRepository.findByQuery(query)
    }
}