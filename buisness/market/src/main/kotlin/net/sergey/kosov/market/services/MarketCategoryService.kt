package net.sergey.kosov.market.services

import net.sergey.kosov.common.exceptions.NotFoundException
import net.sergey.kosov.market.api.AccountApi
import net.sergey.kosov.market.domains.entity.Account
import net.sergey.kosov.market.domains.entity.Category
import net.sergey.kosov.market.domains.entity.Category._Category.ACCOUNT
import net.sergey.kosov.market.domains.entity.Category._Category.ID
import net.sergey.kosov.market.domains.entity.Characteristic
import net.sergey.kosov.market.domains.view.wrappers.CategoryViewCreation
import net.sergey.kosov.market.repository.category.CategoryRepository
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service

@Service
class MarketCategoryService(var categoryRepository: CategoryRepository,
                            var accountApi: AccountApi) : CategoryService {

    override fun create(categoryViewCreation: CategoryViewCreation, ownerName: String): Category {
        val account: Account = accountApi.getAccount(ownerName)
        val parentCategory = findParentCategory(categoryViewCreation.parentId, ownerName)
        val category = Category(title = categoryViewCreation.title,
                description = categoryViewCreation.description,
                account = account,
                characteristics = categoryViewCreation.characteristics,
                parent = parentCategory)
        return categoryRepository.insert(category)
    }

    private fun findParentCategory(parentId: String?, name: String): Category? {
        return if (parentId != null) {
            findCategoryById(parentId, name)
        } else {
            null
        }
    }

    override fun setCharacteristics(categoryId: String, characteristics: List<Characteristic>, name: String): Category {
        val category = findCategoryById(categoryId, name)
        category.characteristics = characteristics
        return categoryRepository.save(category)
    }

    override fun findCategoryById(categoryId: String, name: String): Category {
        val account: Account = accountApi.getAccount(name)
        val query = Query(Criteria.where(ID).`is`(categoryId))
                .addCriteria(getAvailableCategories(account))
        val categories = categoryRepository.findByQuery(query)

        if (categories.size == 1) {
            return categories.first()
        } else {
            throw NotFoundException("can not fount category by id = $categoryId")
        }
    }

    override fun getCategories(name: String): List<Category> {
        val account: Account = accountApi.getAccount(name)
        val query = Query(getAvailableCategories(account))
        return categoryRepository.findByQuery(query)
    }

    private fun getAvailableCategories(account: Account) =
            Criteria().orOperator(Criteria.where(ACCOUNT).`is`(account), Criteria.where(ACCOUNT).`is`(null))
}

















