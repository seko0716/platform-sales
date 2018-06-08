package net.sergey.kosov.market.repository.category

import net.sergey.kosov.market.domains.Category
import net.sergey.kosov.market.repository.RepositoryQuery
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class CategoryRepositoryImpl @Autowired constructor(var mongoTemplate: MongoTemplate) : RepositoryQuery<Category> {

    override fun findByQuery(query: Query): List<Category> {
        return mongoTemplate.find(query, Category::class.java)
    }
}

