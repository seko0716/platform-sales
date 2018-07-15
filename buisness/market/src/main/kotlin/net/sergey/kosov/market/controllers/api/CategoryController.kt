package net.sergey.kosov.market.controllers.api

import net.sergey.kosov.market.domains.entity.Category
import net.sergey.kosov.market.domains.view.wrappers.CategoryViewCreation
import net.sergey.kosov.market.services.CategoryService
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
class CategoryController(val categoryService: CategoryService) {
    @PutMapping("/category")
    fun create(@RequestBody categoryViewCreation: CategoryViewCreation, principal: Principal): Category {
        return categoryService.create(categoryViewCreation = categoryViewCreation, currentUserName = principal.name)
    }

    @GetMapping("/category/{accountId}/{categoryId}")
    fun find(@PathVariable("categoryId") categoryId: String,
             @PathVariable("accountId") accountId: String, principal: Principal): Category {
        return categoryService.findCategoryById(categoryId = categoryId, accountId = accountId, currentUserName = principal.name)
    }

    @GetMapping("/{accountId}/categories")
    fun getCategories(principal: Principal, @PathVariable("accountId") accountId: String): List<Category> {
        return categoryService.getCategories(currentUserName = principal.name, accountId = accountId)
    }
}