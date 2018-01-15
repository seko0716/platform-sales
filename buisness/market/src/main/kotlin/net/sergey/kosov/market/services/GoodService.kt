package net.sergey.kosov.market.services

import net.sergey.kosov.market.domains.Goods
import org.bson.types.ObjectId

interface GoodService {
    fun findGoodsById(id: String): Goods
    fun findGoodsById(id: ObjectId): Goods
    fun getGoods4Chart(): List<Goods>
    fun getGoods4ChartForUser(userId: String): List<Goods>
    fun disabledGoods(goodsId: String): Goods
    fun enabledGoods(goodsId: String): Goods
    fun createGoods(description: String, title: String): Goods

}