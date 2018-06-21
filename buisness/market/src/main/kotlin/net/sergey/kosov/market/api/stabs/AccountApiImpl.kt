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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    var account: Account? = null
    override fun getAccount(name: String): Account {
        if (account == null) {
            account = Account(marketName = name, description = "")
        }
        return account!!
    }
}