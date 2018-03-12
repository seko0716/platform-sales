package net.sergey.kosov.market.repository.order

import net.sergey.kosov.market.domains.Order
import net.sergey.kosov.market.repository.RepositoryQuery
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class OrderRepositoryImpl @Autowired constructor(var mongoTemplate: MongoTemplate) : RepositoryQuery<Order> {

    override fun findByQuery(query: Query): List<Order> {
        return mongoTemplate.find(query, Order::class.java)
    }
}

