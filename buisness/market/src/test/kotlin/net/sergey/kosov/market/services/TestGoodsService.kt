package net.sergey.kosov.market.services

import net.sergey.kosov.market.domains.Goods
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
class TestGoodsService {
    @Autowired
    lateinit var goodsService: GoodService

    @Test
    fun findGoodsById() {
        var goods: Goods = goodsService.findGoodsById(id = "")
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
    fun createGoods() {

    }

    @Test
    fun disabledGoods() {
        var goods: Goods = goodsService.disabledGoods(goodsId = "")
    }

    @Test
    fun enabledGoods() {
        var goods: Goods = goodsService.enabledGoods(goodsId = "")
    }

}

