package net.sergey.kosov.market.repository.category

import net.sergey.kosov.market.domains.entity.Category
import net.sergey.kosov.market.repository.RepositoryQuery
import org.springframework.data.mongodb.repository.MongoRepository

interface CategoryRepository : MongoRepository<Category, String>, RepositoryQuery<Category>