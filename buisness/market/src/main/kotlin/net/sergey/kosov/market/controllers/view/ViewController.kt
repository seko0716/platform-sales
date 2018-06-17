package net.sergey.kosov.market.controllers.view

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ViewController {

    @GetMapping("/view/products")
    fun products(): String {
        return "products"
    }
}