package net.sergey.kosov.market.api

import net.sergey.kosov.market.domains.entity.Order
import org.springframework.cloud.netflix.feign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient(name = "statistic-service")
interface StatisticApi {
    @RequestMapping(path = ["/orderAction"], method = [RequestMethod.PUT], consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun orderAction(@RequestBody order: Order): Order
}