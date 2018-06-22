package net.sergey.kosov.market.controllers.view

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ViewController {

    @GetMapping("/view/products")
    fun products(): String {
        return "products"
    }

    @GetMapping("/view/product")
    fun product(): String {
        return "product"
    }

    @GetMapping("/view/create_category")
    fun category(): String {
        return "create_category"
    }

    @GetMapping("/view/create_product")
    fun createProduct(): String {
        return "create_product"
    }

    @GetMapping("/view/shop")
    fun shop(): String {
        return "shop"
    }

}