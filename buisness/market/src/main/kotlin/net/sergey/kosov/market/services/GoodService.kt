package net.sergey.kosov.market.services

import net.sergey.kosov.market.domains.Goods

interface GoodService {
    fun findGoodsById(id: String): Goods
    fun getGoods4Chart(): List<Goods>
    fun getGoods4ChartForUser(userId: String): List<Goods>
    fun disabledGoods(goodsId: String): Goods
    fun enabledGoods(goodsId: String): Goods

}