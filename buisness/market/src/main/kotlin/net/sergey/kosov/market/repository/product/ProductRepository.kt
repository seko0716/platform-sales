package net.sergey.kosov.market.repository.product

import net.sergey.kosov.market.domains.Product
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : MongoRepository<Product, ObjectId>