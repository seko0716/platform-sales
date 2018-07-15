package net.sergey.kosov.account.repositories

import net.sergey.kosov.account.domains.User
import net.sergey.kosov.common.repositories.RepositoryQuery
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl @Autowired constructor(var mongoTemplate: MongoTemplate) : RepositoryQuery<User> {
    override fun findOneByQuery(query: Query): User? {
        return mongoTemplate.findOne(query, User::class.java)
    }

    override fun findByQuery(query: Query): List<User> {
        return mongoTemplate.find(query, User::class.java)
    }
}

