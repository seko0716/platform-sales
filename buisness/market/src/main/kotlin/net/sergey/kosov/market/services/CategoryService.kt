package net.sergey.kosov.market.services

import net.sergey.kosov.market.domains.entity.Account
import net.sergey.kosov.market.domains.entity.Category
import net.sergey.kosov.market.domains.view.wrappers.CategoryViewCreation

interface CategoryService {
    fun findCategoryById(categoryId: String, account: Account, currentUserName: String): Category
    fun create(categoryViewCreation: CategoryViewCreation, currentUserName: String): Category
    fun getCategories(currentUserName: String, accountId: String): List<Category>
}