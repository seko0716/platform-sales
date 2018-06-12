package net.sergey.kosov.market.services

import net.sergey.kosov.market.api.AccountApi
import net.sergey.kosov.market.api.StatisticApi
import net.sergey.kosov.market.configuration.ConfigurationFeign
import net.sergey.kosov.market.domains.entity.Account
import net.sergey.kosov.market.domains.entity.Category
import net.sergey.kosov.market.domains.entity.Product
import net.sergey.kosov.market.domains.view.wrappers.ProductViewCreation
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.ContextHierarchy
import org.springframework.test.context.junit4.SpringRunner
import java.math.BigDecimal

@RunWith(SpringRunner::class)
@ContextHierarchy(ContextConfiguration(classes = [ServiceConfig::class, ConfigurationFeign::class]))
class TestProductService {
    @Autowired
    private lateinit var productService: ProductService
    @MockBean
    private lateinit var statisticService: StatisticApi
    @MockBean
    private lateinit var accountApi: AccountApi
    @MockBean
    private lateinit var categoryService: CategoryService
    private var goodsChartIds: List<String>? = null

    @Before
    fun before() {
        Mockito.doReturn(Account("1", "1")).`when`(accountApi).getAccount("1")

        Mockito.doReturn(Category(title = "1")).`when`(categoryService).findCategoryById("", "1")

        goodsChartIds = (1..10).mapTo(ArrayList()) {
            productService.createProduct(
                    ProductViewCreation(title = "name!!$it", description = "description!!", categoryId = "", price = BigDecimal.ZERO),
                    "1").id.toString()
        }
        Mockito.doReturn(goodsChartIds).`when`(statisticService).getChart("name", 100)
    }

    @Test
    fun findGoodsById() {
        val productViewCreation = ProductViewCreation(title = "name!!", description = "description!!", categoryId = "", price = BigDecimal.ZERO)
        val goodsCreated = productService.createProduct(productViewCreation, "1")
        val product: Product = productService.findProductById(id = goodsCreated.id.toString())
        Assert.assertEquals(goodsCreated.id, product.id)
    }

    @Test
    fun getGoods4Chart() {
        val productList: List<Product> = productService.getProducts4Chart("name")
        Assert.assertTrue(productList.all { goodsChartIds!!.contains(it.id.toString()) })
    }

    @Test
    fun disabledGoods() {
        val productViewCreation = ProductViewCreation(title = "name!!", description = "description!!", categoryId = "", price = BigDecimal.ZERO)
        val goodsCreated = productService.createProduct(productViewCreation, "1")
        val disabledGoods = productService.disabledProduct(goodsCreated.id.toString())
        Assert.assertEquals(goodsCreated.id, disabledGoods.id)
        Assert.assertEquals(false, disabledGoods.enabled)
    }

    @Test
    fun enabledGoods() {
        val productViewCreation = ProductViewCreation(title = "name!!", description = "description!!", categoryId = "", price = BigDecimal.ZERO)
        val goodsCreated = productService.createProduct(productViewCreation, "1")
        val enabledGoods = productService.enabledProduct(goodsCreated.id.toString())
        Assert.assertEquals(goodsCreated.id, enabledGoods.id)
        Assert.assertEquals(true, enabledGoods.enabled)
    }

}

