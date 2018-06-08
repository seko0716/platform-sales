package net.sergey.kosov.market.services

import com.sun.security.auth.UserPrincipal
import net.sergey.kosov.market.api.StatisticApi
import net.sergey.kosov.market.configuration.ConfigurationFeign
import net.sergey.kosov.market.domains.Product
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

@RunWith(SpringRunner::class)
@ContextHierarchy(ContextConfiguration(classes = [GoodsServiceConfig::class, ConfigurationFeign::class]))
class TestProductService {
    @Autowired
    private lateinit var productService: ProductService
    @MockBean
    private lateinit var statisticService: StatisticApi
    private var goodsChartIds: List<String>? = null
    @Before
    fun before() {
        goodsChartIds = (1..10).mapTo(ArrayList()) { productService.createProduct(title = "name$it", description = "description$it").id.toString() }
        Mockito.doReturn(goodsChartIds).`when`(statisticService).getChart("name", 100)
    }

    @Test
    fun findGoodsById() {
        val goodsCreated = productService.createProduct(title = "name", description = "description")
        val product: Product = productService.findProductById(id = goodsCreated.id.toString())
        Assert.assertEquals(goodsCreated.id, product.id)
    }

    @Test
    fun getGoods4Chart() {
        var productList: List<Product> = productService.getProducts4Chart(UserPrincipal("name"))
        Assert.assertTrue(productList.all { goodsChartIds!!.contains(it.id.toString()) })
    }

    @Test
    fun disabledGoods() {
        val goodsCreated = productService.createProduct(title = "name2", description = "description2")
        val disabledGoods = productService.disabledProduct(goodsCreated)
        Assert.assertEquals(goodsCreated.id, disabledGoods.id)
        Assert.assertEquals(false, disabledGoods.enabled)
    }

    @Test
    fun enabledGoods() {
        val goodsCreated = productService.createProduct(title = "name3", description = "description3")
        val enabledGoods = productService.enabledProduct(goodsCreated)
        Assert.assertEquals(goodsCreated.id, enabledGoods.id)
        Assert.assertEquals(true, enabledGoods.enabled)
    }

}

