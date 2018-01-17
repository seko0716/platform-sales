package net.sergey.kosov.market.repository

import net.sergey.kosov.market.domains.Order
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : MongoRepository<Order, ObjectId>