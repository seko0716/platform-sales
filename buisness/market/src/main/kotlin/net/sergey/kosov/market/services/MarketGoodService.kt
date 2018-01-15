package net.sergey.kosov.market.services

import net.sergey.kosov.market.domains.Goods
import net.sergey.kosov.market.repository.GoodsRepository
import org.bson.types.ObjectId
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.stereotype.Service

@Service
class MarketGoodService(var goodsRepository: GoodsRepository) : GoodService {

    override fun findGoodsById(id: String): Goods {
        return findGoodsById(ObjectId(id))
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createGoods(description: String, title: String): Goods {
        val goods = Goods(title = title, description = description)
        return goodsRepository.save(goods) ?: throw IllegalStateException("не смог сохранить")
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findGoodsById(id: ObjectId): Goods {
        return goodsRepository.findOne(id) ?: throw ChangeSetPersister.NotFoundException()
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getGoods4Chart(): List<Goods> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getGoods4ChartForUser(userId: String): List<Goods> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun disabledGoods(goodsId: String): Goods {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun enabledGoods(goodsId: String): Goods {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}