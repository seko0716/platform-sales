package net.sergey.kosov.statistic.controllers

import net.sergey.kosov.statistic.domains.FullProduct
import net.sergey.kosov.statistic.domains.Product
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RestController
import java.security.Principal
import javax.servlet.http.HttpSession

@RestController
class RestController {
    @GetMapping(path = ["/recentlyViewed/{count}"], consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun getRecentlyViewed(principal: Principal, @PathVariable("count") chartSize: Int): List<Product> {
        return listOf()
    }

    @GetMapping(path = ["/companions/{productId}/{count}"], consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun getCompanions(principal: Principal,
                      @PathVariable("productId") productId: String,
                      @PathVariable("count") chartSize: Int): List<Product> {
        return listOf()
    }

    @PutMapping("/viewing")
    fun viewing(session: HttpSession, @RequestBody product: Product) {
        val sessionId: String = session.id
        //todo add in kafka topic
    }

    @PostMapping("/validateProduct")
    fun validateProduct(product: FullProduct): Double {
//        todo отдеть нейронке и получить результат (пока предположительно процент актуальности продукта)
        return 0.0
    }

}