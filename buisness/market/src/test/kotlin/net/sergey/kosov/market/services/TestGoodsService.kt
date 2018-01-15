package net.sergey.kosov.market.services

import net.sergey.kosov.market.domains.Goods
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.ContextHierarchy
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@ContextHierarchy(ContextConfiguration(classes = [GoodsServiceConfig::class]))
class TestGoodsService {
    @Autowired
    private lateinit var goodsService: GoodService

    @Test
    fun findGoodsById() {
        val goodsCreated = goodsService.createGoods(title = "name", description = "description")
        val goods: Goods = goodsService.findGoodsById(id = goodsCreated.id)
        Assert.assertEquals(goodsCreated.id, goods.id)
    }

    @Test
    fun getGoods4Chart() {
        var goodsList: List<Goods> = goodsService.getGoods4Chart()
    }

    @Test
    fun getGoods4ChartForUser() {
        var goodsList: List<Goods> = goodsService.getGoods4ChartForUser(userId = "")
    }

    @Test
    fun disabledGoods() {
        val goodsCreated = goodsService.createGoods(title = "name2", description = "description2")
        val disabledGoods = goodsService.disabledGoods(goodsCreated)
        Assert.assertEquals(goodsCreated.id, disabledGoods.id)
        Assert.assertEquals(false, disabledGoods.enabled)
    }

    @Test
    fun enabledGoods() {
        val goodsCreated = goodsService.createGoods(title = "name3", description = "description3")
        val enabledGoods = goodsService.enabledGoods(goodsCreated)
        Assert.assertEquals(goodsCreated.id, enabledGoods.id)
        Assert.assertEquals(true, enabledGoods.enabled)
    }

}

