package net.sergey.kosov.market.api.stabs

import net.sergey.kosov.market.api.AccountApi
import net.sergey.kosov.market.domains.entity.Account
import net.sergey.kosov.market.domains.entity.User
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Profile("test")
@Component
class AccountApiImpl : AccountApi {
    override fun getUser(username: String): User {
        return User(name = username, family = "family")
    }


    override fun getAccount(name: String): Account {
        return Account(marketName = name, description = "")
    }
}