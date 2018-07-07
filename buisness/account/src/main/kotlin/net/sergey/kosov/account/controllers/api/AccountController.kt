package net.sergey.kosov.account.controllers.api

import net.sergey.kosov.account.domains.Account
import net.sergey.kosov.account.domains.ViewCreationAccount
import net.sergey.kosov.account.services.AccountService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
class AccountController(var accountService: AccountService) {

    @GetMapping(path = ["/account/{name}"])
    fun getAccount(@PathVariable("name") name: String): Account {
        return accountService.getAccount(name)
    }

    @PreAuthorize("permitAll()")
    @PutMapping(path = ["/create"])
    fun createAccount(@RequestBody viewCreationAccount: ViewCreationAccount): Account {
        return accountService.createAccount(viewCreationAccount)
    }
}