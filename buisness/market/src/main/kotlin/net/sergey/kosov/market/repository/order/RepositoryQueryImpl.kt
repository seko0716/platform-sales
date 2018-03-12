package net.sergey.kosov.market.repository.order

import net.sergey.kosov.market.domains.Order
import net.sergey.kosov.market.domains.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import javax.annotation.PostConstruct

@Repository
class RepositoryQueryImpl<T, Id> @Autowired constructor(var mongoTemplate: MongoTemplate) : RepositoryQuery<T, Id> {

    override fun findByTitle(query: Query, clazz: Class<T>): List<T> {
        mongoTemplate.find(query, Order::class.java)
        val find = mongoTemplate.find(query, clazz)
        return find
    }


    @PostConstruct
    fun init(): Unit {
        println("##############")
        var query = Query()
        query.addCriteria(Criteria.where("customer").`is`(User("", "")))
        val find = mongoTemplate.find(query, Order::class.java)
        println("##############")
    }
}

