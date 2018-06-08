package net.sergey.kosov.market.repository.order

import net.sergey.kosov.market.domains.Order
import net.sergey.kosov.market.repository.RepositoryQuery
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : MongoRepository<Order, ObjectId>, RepositoryQuery<Order>