package net.sergey.kosov.account.controllers.api

import net.sergey.kosov.account.domains.Account
import net.sergey.kosov.account.services.AccountService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController


@CrossOrigin
@RestController
class AccountController(var accountService: AccountService) {

    @PreAuthorize("permitAll()")
    @GetMapping(path = ["/account/{name}"])
    fun getAccount(@PathVariable("name") name: String): Account {
        return accountService.getAccount(name)
    }

}