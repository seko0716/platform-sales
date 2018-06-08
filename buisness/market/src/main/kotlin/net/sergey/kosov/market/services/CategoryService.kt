package net.sergey.kosov.market.services

import net.sergey.kosov.market.domains.Category

interface CategoryService {
    fun findCategoryById(categoryId: String): Category
}