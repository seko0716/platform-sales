package net.sergey.kosov.market.repository.category

import net.sergey.kosov.market.domains.Category
import org.springframework.data.mongodb.repository.MongoRepository

interface CategoryRepository : MongoRepository<Category, String>