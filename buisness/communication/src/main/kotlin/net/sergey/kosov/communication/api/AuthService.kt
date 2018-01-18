package net.sergey.kosov.communication.api

import org.springframework.cloud.netflix.feign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient(name = "auth-service")
interface AuthService {
    @RequestMapping(path = ["/token/{protocol}"], method = [RequestMethod.GET], consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun getToken(@PathVariable("protocol") protocol: String): String
}