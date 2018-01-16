package net.sergey.kosov.market.api

import org.bson.types.ObjectId
import org.springframework.cloud.netflix.feign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient(name = "statistic-service")
interface StatisticApi {
    @RequestMapping(value = ["/chart/{count}"], method = [RequestMethod.GET], consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun getChart(username: String?, @PathVariable("count") chartSize: Int): List<ObjectId>

}