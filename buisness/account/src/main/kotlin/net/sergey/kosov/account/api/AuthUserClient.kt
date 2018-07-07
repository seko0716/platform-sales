package net.sergey.kosov.account.api

import net.sergey.kosov.account.domains.User
import org.springframework.cloud.netflix.feign.FeignClient
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod.POST

@FeignClient(name = "auth-service")
interface AuthUserClient {
    @RequestMapping("/uaa/users", method = [POST], consumes = [APPLICATION_JSON_UTF8_VALUE])
    fun createUser(user: User.User)
}
