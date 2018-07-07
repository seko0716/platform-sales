package net.sergey.kosov.apigetewaynservice.controllers.view

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ViewController {

    @GetMapping("/view/products")
    String products() {
        return "products"
    }

    @GetMapping("/view/product/*")
    String product() {
        return "product"
    }

    @GetMapping("/view/create_category")
    String category() {
        return "create_category"
    }

    @GetMapping("/view/create_product")
    String createProduct() {
        return "create_product"
    }

    @GetMapping("/view/shop/*")
    String shop() {
        return "shop"
    }

    @GetMapping("/view/cart")
    String cart() {
        return "cart"
    }

    @GetMapping("/view/order/*")
    String order() {
        return "order"
    }

    @GetMapping("/view/orders")
    String orders() {
        return "orders"
    }

    @GetMapping("/view/login")
    String login() {
        return "login"
    }
}