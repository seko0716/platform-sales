package net.sergey.kosov.market.services

import net.sergey.kosov.common.exceptions.NotFoundException
import net.sergey.kosov.market.api.AccountApi
import net.sergey.kosov.market.domains.entity.Account
import net.sergey.kosov.market.domains.entity.Category
import net.sergey.kosov.market.domains.entity.Category._Category.ACCOUNT
import net.sergey.kosov.market.domains.entity.Category._Category.ID
import net.sergey.kosov.market.domains.view.wrappers.CategoryViewCreation
import net.sergey.kosov.market.repository.category.CategoryRepository
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service

@Service
class MarketCategoryService(var categoryRepository: CategoryRepository,
                            var accountApi: AccountApi) : CategoryService {

    override fun create(categoryViewCreation: CategoryViewCreation, currentUserName: String): Category {
        val account: Account = accountApi.getAccount(currentUserName, categoryViewCreation.accountId)
        val parentCategory = findParentCategory(categoryViewCreation.parentId, currentUserName, account)
        val category = Category(title = categoryViewCreation.title,
                description = categoryViewCreation.description,
                account = account,
                characteristics = categoryViewCreation.characteristics,
                parent = parentCategory)
        return categoryRepository.insert(category)
    }

    private fun findParentCategory(parentId: String?, name: String, account: Account): Category? {
        return parentId?.let { findCategoryById(it, account, name) }
    }

    override fun findCategoryById(categoryId: String, account: Account, currentUserName: String): Category {
        return categoryRepository.findOneByQuery(Query(getCriteriaCategoryId(categoryId))
                .addCriteria(getAvailableCategories(account)))
                ?: throw NotFoundException("can not fount category by id = $categoryId")

    }

    override fun getCategories(currentUserName: String, accountId: String): List<Category> {
        val account: Account = accountApi.getAccount(currentUserName, accountId)
        return categoryRepository.findByQuery(Query(getAvailableCategories(account)))
    }

    private fun getAvailableCategories(account: Account) =
            Criteria().orOperator(Criteria.where(ACCOUNT).`is`(account), Criteria.where(ACCOUNT).`is`(null))

    private fun getCriteriaCategoryId(categoryId: String) = Criteria.where(ID).`is`(categoryId)
}
