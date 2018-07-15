package net.sergey.kosov.account.controllers.api

import net.sergey.kosov.account.domains.Account
import net.sergey.kosov.account.domains.ViewCreationAccount
import net.sergey.kosov.account.domains.ViewUpdateAccount
import net.sergey.kosov.account.services.AccountService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
class AccountController(var accountService: AccountService) {

    @GetMapping(path = ["/account/{userName}/{accountId}"])
//    @PreAuthorize("#oauth2.hasScope('server')")
    fun getAccount(@PathVariable("userName") userName: String, @PathVariable("accountId") accountId: String): Account {
        return accountService.getAccount(userName, accountId)
    }

    @GetMapping(path = ["/account/{marketName}"])
    fun getAccountByMarketName(@PathVariable("marketName") marketName: String): Account {
        return accountService.getAccount(marketName)
    }

    @GetMapping(path = ["/current/account"])
    fun getCurrentAccount(principal: Principal): Account {
        return accountService.getAccountByUser(principal.name)
    }

    @PreAuthorize("permitAll()")
    @PutMapping(path = ["/create"])
    fun createAccount(@RequestBody viewCreationAccount: ViewCreationAccount): Account {
        return accountService.createAccount(viewCreationAccount)
    }

    @PostMapping(path = ["/addUser/{marketName}/{uuid}"])
    fun addUser(principal: Principal, @PathVariable marketName: String, uuid: String): Account {
        return accountService.addUser(principal.name, marketName, uuid)
    }

    @PostMapping(path = ["/update"])
    fun updateAccount(principal: Principal, @RequestBody viewUpdateAccount: ViewUpdateAccount): Account {
        return accountService.updateAccount(principal.name, viewUpdateAccount)
    }
}