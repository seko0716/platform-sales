package net.sergey.kosov.market.services

import net.sergey.kosov.common.exceptions.NotFoundException
import net.sergey.kosov.market.api.AccountApi
import net.sergey.kosov.market.api.StatisticApi
import net.sergey.kosov.market.domains.entity.Category
import net.sergey.kosov.market.domains.entity.Characteristic
import net.sergey.kosov.market.domains.entity.Product
import net.sergey.kosov.market.domains.entity.Product._Product
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

    override fun getProducts4Market(marketName: String): List<Product> {
        return productRepository.findByQuery(getProductQuery().addCriteria(getAccountCriteria(marketName)))
    }

    override fun createProduct(productViewCreation: ProductViewCreation, userName: String): Product {
        val account = getAccount(userName, productViewCreation.accountId)
        val category = categoryService.findCategoryById(productViewCreation.categoryId, account, userName)
        val tags = calculateTags(productViewCreation, category)

        val products = Product(title = productViewCreation.title,
                description = productViewCreation.description,
                account = account,
                productInfo = productViewCreation.productInfo,
                price = productViewCreation.price,
                category = category,
                characteristic = productViewCreation.characteristics,
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
        return category.flatMap { c -> c.characteristics.map { it.name } }
    }

    private fun getAccount(userName: String, accountId: String) = accountApi.getAccount(userName, accountId)

    override fun findProductById(id: String): Product {
        return productRepository.findOne(id) ?: throw NotFoundException("can not found product by id $id")
    }

    override fun getProducts4Chart(userName: String): List<Product> {
        val productsIdsChart = statisticApi.getChart(username = userName, chartSize = chartSize)
        return productRepository.findAll(productsIdsChart).toList()
    }

    override fun disabledProduct(id: String) = productRepository.save(findProductById(id).apply { enabled = false })

    override fun enabledProduct(id: String) = productRepository.save(findProductById(id).apply { enabled = true })

    override fun findProducts(filter: ProductFilter): List<Product> {
        return productRepository.findByQuery(getProductQuery()
                .addCriteria(getCriteriaBetweenPrice(filter))
                .addCriteria(getCriteriaTitleRegex(filter)))
    }

    override fun findProducts(): List<Product> = productRepository.findByQuery(getProductQuery().limit(100))


    private fun getAccountCriteria(marketName: String) = Criteria.where("${_Product.ACCOUNT}.marketName").`is`(marketName)

    private fun getCriteriaBetweenPrice(filter: ProductFilter) =
            Criteria().andOperator(Criteria.where(_Product.PRICE).gte(filter.priceLeft),
                    Criteria.where(_Product.PRICE).lte(filter.priceRight))

    private fun getCriteriaTitleRegex(filter: ProductFilter) = Criteria.where(_Product.TITLE).regex(filter.title)

    private fun getProductQuery() = Query(Criteria.where(_Product.ENABLED).`is`(true))
}