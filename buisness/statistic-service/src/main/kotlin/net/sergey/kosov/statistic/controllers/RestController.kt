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
    @PreAuthorize("permitAll()")
    @GetMapping(path = ["/recentlyViewed"], consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun getRecentlyViewed(principal: Principal?, httpSession: HttpSession): List<Map<String, Object>> {
        return productService.getRecentlyViewed(principal?.name, httpSession)
    }

    @PreAuthorize("permitAll()")
    @GetMapping(path = ["/companions/{productId}"], consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun getCompanions(@PathVariable("productId") productId: String): List<Map<String, Object>> {
        return productService.getCompanions(productId = productId)
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