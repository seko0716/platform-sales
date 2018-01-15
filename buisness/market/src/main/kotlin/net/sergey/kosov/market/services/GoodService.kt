package net.sergey.kosov.market.services

import net.sergey.kosov.market.domains.Goods
import org.bson.types.ObjectId

interface GoodService {
    fun findGoodsById(id: ObjectId): Goods
    fun getGoods4Chart(): List<Goods>
    fun getGoods4ChartForUser(userId: String): List<Goods>
    fun disabledGoods(goodsId: ObjectId): Goods
    fun disabledGoods(goods: Goods): Goods
    fun enabledGoods(goodsId: ObjectId): Goods
    fun enabledGoods(goods: Goods): Goods
    fun createGoods(description: String, title: String): Goods

}