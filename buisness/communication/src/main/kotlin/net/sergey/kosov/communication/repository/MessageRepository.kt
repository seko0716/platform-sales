package net.sergey.kosov.communication.repository

import net.sergey.kosov.common.repositories.RepositoryQuery
import net.sergey.kosov.communication.domains.Message
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface MessageRepository : MongoRepository<Message, ObjectId>, RepositoryQuery<Message>