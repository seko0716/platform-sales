package net.sergey.kosov.market.repository

import net.sergey.kosov.market.domains.Goods
import org.bson.types.ObjectId
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface GoodsRepository : CrudRepository<Goods, ObjectId> {
}