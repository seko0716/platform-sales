package net.sergey.kosov.statistic.controllers

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class RestController {

    @GetMapping(path = ["/chart/{count}"], consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun getChart(username: String?, @PathVariable("count") chartSize: Int): List<String> {
        return ArrayList()
    }
}