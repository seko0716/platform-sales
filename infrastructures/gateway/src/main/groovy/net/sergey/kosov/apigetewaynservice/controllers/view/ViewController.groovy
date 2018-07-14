package net.sergey.kosov.apigetewaynservice.controllers.view

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/view")
class ViewController {

    @GetMapping("/products")
    String products() {
        return "products"
    }

    @GetMapping("/product/*")
    String product() {
        return "product"
    }

    @GetMapping("/shop/*")
    String shop() {
        return "shop"
    }

    @GetMapping("/cart")
    String cart() {
        return "cart"
    }

    @GetMapping("/order/*")
    String order() {
        return "order"
    }

    @GetMapping("/orders")
    String orders() {
        return "orders"
    }

    @GetMapping("/login")
    String login() {
        return "login"
    }

    @GetMapping("/registration")
    String registration() {
        return "registration"
    }

    @GetMapping("/account")
    String account() {
        return "account"
    }
}