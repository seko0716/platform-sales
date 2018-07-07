package net.sergey.kosov.market.api

import net.sergey.kosov.market.domains.entity.Account
import net.sergey.kosov.market.domains.entity.User
import org.springframework.cloud.netflix.feign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient(name = "account-service")
interface AccountApi {
    @RequestMapping(path = ["/account/user/{name}"], method = [RequestMethod.GET], consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun getUser(@PathVariable("name") username: String): User

    @RequestMapping(path = ["/account/account/{name}"], method = [RequestMethod.GET], consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun getAccount(@PathVariable("name") name: String): Account

}