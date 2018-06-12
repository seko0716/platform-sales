package net.sergey.kosov.market.repository.product

import net.sergey.kosov.market.domains.entity.Product
import net.sergey.kosov.market.repository.RepositoryQuery
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class ProductRepositoryImpl @Autowired constructor(var mongoTemplate: MongoTemplate) : RepositoryQuery<Product> {

    override fun findByQuery(query: Query): List<Product> {
        return mongoTemplate.find(query, Product::class.java)
    }
}

