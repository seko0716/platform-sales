package net.sergey.kosov.market.services

import net.sergey.kosov.market.domains.entity.Category
import net.sergey.kosov.market.domains.entity.Characteristic
import net.sergey.kosov.market.domains.view.wrappers.CategoryViewCreation

interface CategoryService {
    fun findCategoryById(categoryId: String, name: String): Category
    fun create(categoryViewCreation: CategoryViewCreation, ownerName: String): Category
    fun setCharacteristics(categoryId: String, characteristics: List<Characteristic>, name: String): Category
    fun getCategories(name: String): List<Category>
}