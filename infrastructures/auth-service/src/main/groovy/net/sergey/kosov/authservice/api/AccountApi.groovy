package net.sergey.kosov.authservice.api

import net.sergey.kosov.authservice.domains.Account
import net.sergey.kosov.authservice.domains.ViewCreationAccount
import org.springframework.cloud.netflix.feign.FeignClient
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE

@FeignClient(name = "auth-service")
interface AccountApi {
    @RequestMapping(path = ["/create/social"], method = RequestMethod.PUT, consumes = [APPLICATION_JSON_UTF8_VALUE])
    Account createAccountSocial(@RequestBody ViewCreationAccount viewCreationAccount)
}
