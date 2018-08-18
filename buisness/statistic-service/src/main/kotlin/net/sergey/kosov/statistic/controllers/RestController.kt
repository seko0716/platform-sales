package net.sergey.kosov.statistic.controllers

import net.sergey.kosov.statistic.domains.Product
import net.sergey.kosov.statistic.services.KafkaService
import net.sergey.kosov.statistic.services.ProductService
import org.omg.CORBA.Object
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RestController
import java.security.Principal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.servlet.http.HttpSession

@RestController
class RestController @Autowired constructor(val kafkaService: KafkaService, val productService: ProductService) {
    @GetMapping(path = ["/recentlyViewed"], consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun getRecentlyViewed(principal: Principal?, httpSession: HttpSession): List<Map<String, Object>> {
        return productService.getRecentlyViewed(principal?.name, httpSession)
    }

    @GetMapping(path = ["/companions/{productId}/{count}"], consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun getCompanions(principal: Principal,
                      @PathVariable("productId") productId: String,
                      @PathVariable("count") chartSize: Int): List<Product> {
        return listOf()
    }

    @PreAuthorize("permitAll()")
    @PutMapping("/viewing")
    fun viewing(session: HttpSession, principal: Principal?, @RequestBody product: Product) {
        kafkaService.send(session, principal?.name, product.apply { date = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) })
    }

    @PostMapping("/validateProduct")
    fun validateProduct(product: Product): Double {
//        todo отдеть нейронке и получить результат (пока предположительно процент актуальности продукта)
        return 0.0
    }
}