package net.sergey.kosov.market.repository.category

import net.sergey.kosov.common.repositories.RepositoryQuery
import net.sergey.kosov.market.domains.entity.Category
import org.springframework.data.mongodb.repository.MongoRepository

interface CategoryRepository : MongoRepository<Category, String>, RepositoryQuery<Category>