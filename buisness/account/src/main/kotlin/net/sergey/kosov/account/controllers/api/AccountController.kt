package net.sergey.kosov.account.controllers.api

import net.sergey.kosov.account.domains.Account
import net.sergey.kosov.account.services.AccountService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class AccountController(var accountService: AccountService) {

    @GetMapping(path = ["/account/{name}"], consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun getAccount(@PathVariable("name") name: String): Account {
        return accountService.getAccount(name)
    }

}