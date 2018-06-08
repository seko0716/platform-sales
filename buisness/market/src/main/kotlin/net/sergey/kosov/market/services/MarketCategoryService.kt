package net.sergey.kosov.market.services

import net.sergey.kosov.market.domains.Category
import org.springframework.stereotype.Service

@Service
class MarketCategoryService : CategoryService {
    override fun findCategoryById(categoryId: String): Category {
        return Category(title = "testCategory")
    }
}