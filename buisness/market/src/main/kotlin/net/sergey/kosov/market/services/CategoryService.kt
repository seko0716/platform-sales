package net.sergey.kosov.market.services

import net.sergey.kosov.market.domains.entity.Category

interface CategoryService {
    fun findCategoryById(categoryId: String): Category
}