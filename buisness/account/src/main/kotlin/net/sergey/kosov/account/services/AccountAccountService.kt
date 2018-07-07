package net.sergey.kosov.account.services

import net.sergey.kosov.account.domains.Account
import net.sergey.kosov.account.domains.User
import net.sergey.kosov.account.domains.ViewCreationAccount
import net.sergey.kosov.account.repositories.AccountRepository
import net.sergey.kosov.common.exceptions.NotFoundException
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service

@Service
class AccountAccountService(var accountRepository: AccountRepository,
                            var userService: UserService) : AccountService {
    override fun createAccount(viewCreationAccount: ViewCreationAccount): Account {
        val account = Account(marketName = viewCreationAccount.marketName, images = listOf())
        val user = User(fullName = viewCreationAccount.fullName,
                firstName = viewCreationAccount.firstName,
                lastName = viewCreationAccount.lastName,
                email = viewCreationAccount.email,
                password = viewCreationAccount.password,
                birthDay = viewCreationAccount.birthDay,
                country = viewCreationAccount.country,
                gender = viewCreationAccount.gender,
                account = account)
        val createdUser = userService.createUser(user)
        return accountRepository.insert(account)
    }

    override fun getAccount(marketName: String): Account {
        val findByQuery = accountRepository.findByQuery(Query(Criteria.where("marketName").`is`(marketName)))
        if (findByQuery.size != 1) {
            throw NotFoundException("can not found account by marketName: $marketName")
        }
        return findByQuery.first()
    }
}