package net.sergey.kosov.market.services

import net.sergey.kosov.market.domains.Goods
import org.bson.types.ObjectId
import java.security.Principal

interface GoodService {
    fun findGoodsById(id: ObjectId): Goods
    fun getGoods4Chart(principal: Principal): List<Goods>
    fun disabledGoods(goodsId: ObjectId): Goods
    fun disabledGoods(goods: Goods): Goods
    fun enabledGoods(goodsId: ObjectId): Goods
    fun enabledGoods(goods: Goods): Goods
    fun createGoods(title: String, description: String): Goods

}