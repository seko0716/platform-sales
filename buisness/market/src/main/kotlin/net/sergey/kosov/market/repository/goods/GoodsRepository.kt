package net.sergey.kosov.market.repository.goods

import net.sergey.kosov.market.domains.Goods
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface GoodsRepository : MongoRepository<Goods, ObjectId>