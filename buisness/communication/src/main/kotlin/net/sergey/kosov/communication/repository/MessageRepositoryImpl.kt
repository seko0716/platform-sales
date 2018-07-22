package net.sergey.kosov.communication.repository

import net.sergey.kosov.common.repositories.RepositoryQuery
import net.sergey.kosov.communication.domains.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class MessageRepositoryImpl @Autowired constructor(var mongoTemplate: MongoTemplate) : RepositoryQuery<Message> {
    override fun findOneByQuery(query: Query): Message? {
        return mongoTemplate.findOne(query, Message::class.java)
    }

    override fun findByQuery(query: Query): List<Message> {
        return mongoTemplate.find(query, Message::class.java)
    }
}

