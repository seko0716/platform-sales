package net.sergey.kosov.market.repository.order

import net.sergey.kosov.market.domains.entity.Order
import net.sergey.kosov.market.repository.RepositoryQuery
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : MongoRepository<Order, String>, RepositoryQuery<Order>