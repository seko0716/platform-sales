package net.sergey.kosov.account.repositories

import net.sergey.kosov.account.domains.Account
import net.sergey.kosov.common.repositories.RepositoryQuery
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class AccountRepositoryImpl @Autowired constructor(var mongoTemplate: MongoTemplate) : RepositoryQuery<Account> {

    override fun findByQuery(query: Query): List<Account> {
        return mongoTemplate.find(query, Account::class.java)
    }
}

