package net.sergey.kosov.communication.repository

import net.sergey.kosov.communication.domains.Message
import org.bson.types.ObjectId
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MessageRepository : CrudRepository<Message, ObjectId>