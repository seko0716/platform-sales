package net.sergey.kosov.market.repository.goods

import net.sergey.kosov.market.domains.Goods
import net.sergey.kosov.market.repository.RepositoryQuery
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class GoodsRepositoryImpl @Autowired constructor(var mongoTemplate: MongoTemplate) : RepositoryQuery<Goods> {

    override fun findByQuery(query: Query): List<Goods> {
        return mongoTemplate.find(query, Goods::class.java)
    }
}

