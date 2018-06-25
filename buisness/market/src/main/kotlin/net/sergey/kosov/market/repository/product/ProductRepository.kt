package net.sergey.kosov.market.repository.product

import net.sergey.kosov.common.repositories.RepositoryQuery
import net.sergey.kosov.market.domains.entity.Product
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : MongoRepository<Product, String>, RepositoryQuery<Product>