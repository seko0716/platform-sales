package net.sergey.kosov.market.repository.order

import net.sergey.kosov.common.repositories.RepositoryQuery
import net.sergey.kosov.market.domains.entity.Order
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class OrderRepositoryImpl @Autowired constructor(var mongoTemplate: MongoTemplate) : RepositoryQuery<Order> {
    override fun findOneByQuery(query: Query): Order? {
        return mongoTemplate.findOne(query, Order::class.java)
    }

    override fun findByQuery(query: Query): List<Order> {
        return mongoTemplate.find(query, Order::class.java)
    }
}

