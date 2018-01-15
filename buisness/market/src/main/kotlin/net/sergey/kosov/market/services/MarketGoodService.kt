package net.sergey.kosov.market.services

import net.sergey.kosov.market.api.StatisticApi
import net.sergey.kosov.market.domains.Goods
import net.sergey.kosov.market.repository.GoodsRepository
import net.sergey.kosov.market.utils.toObjectId
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.stereotype.Service
import java.security.Principal

@Service
class MarketGoodService(private val goodsRepository: GoodsRepository,
                        private val statisticApi: StatisticApi,
                        @Value("\${chart.size}") private var chartSize: Int) : GoodService {

    override fun createGoods(description: String, title: String): Goods {
        val goods = Goods(title = title, description = description)
        return goodsRepository.save(goods) ?: throw IllegalStateException("не смог сохранить")
    }

    override fun findGoodsById(id: ObjectId): Goods {
        return goodsRepository.findOne(id) ?: throw ChangeSetPersister.NotFoundException()
    }

    override fun getGoods4Chart(principal: Principal): List<Goods> {
        val goodsIdsChart = statisticApi.getChart(username = principal.name, chartSize = chartSize)
        return goodsRepository.findAll(goodsIdsChart.map { it.toObjectId() }).toList()
    }

    override fun disabledGoods(goodsId: ObjectId): Goods = disabledGoods(findGoodsById(goodsId))

    override fun enabledGoods(goodsId: ObjectId): Goods = enabledGoods(findGoodsById(goodsId))

    override fun disabledGoods(goods: Goods): Goods = goodsRepository.save(goods.apply { goods.enabled = false })

    override fun enabledGoods(goods: Goods): Goods = goodsRepository.save(goods.apply { goods.enabled = true })
}