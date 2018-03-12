package net.sergey.kosov.market.repository.order

import net.sergey.kosov.market.domains.Order
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class OrderRepositoryImpl @Autowired constructor(var mongoTemplate: MongoTemplate) : RepositoryQuery<Order> {

    override fun findByTitle(query: Query): List<Order> {
        mongoTemplate.find(query, Order::class.java)
        return mongoTemplate.find(query, Order::class.java)
    }
}

