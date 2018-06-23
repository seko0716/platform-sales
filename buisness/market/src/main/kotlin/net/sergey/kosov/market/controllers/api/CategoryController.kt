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
        return categoryService.create(categoryViewCreation = categoryViewCreation, ownerName = principal.name)
    }

    @GetMapping("/category/{categoryId}")
    fun find(@PathVariable("categoryId") categoryId: String, principal: Principal): Category {
        return categoryService.findCategoryById(categoryId, principal.name)
    }

    @GetMapping("/categories")
    fun getCategories(principal: Principal): List<Category> {
        return categoryService.getCategories(principal.name)
    }

//    @PostMapping("/category/{categoryId}")
//    fun setCharacteristics(@PathVariable("categoryId") categoryId: String,
//                           @RequestBody characteristics: List<Characteristic>, principal: Principal): Category {
//        return categoryService.setCharacteristics(categoryId, characteristics, principal.name)
//    }
}