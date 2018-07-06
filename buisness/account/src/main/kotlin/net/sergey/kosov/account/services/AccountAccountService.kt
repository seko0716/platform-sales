package net.sergey.kosov.account.services

import net.sergey.kosov.account.domains.Account
import net.sergey.kosov.account.repositories.AccountRepository
import net.sergey.kosov.common.exceptions.NotFoundException
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service

@Service
class AccountAccountService(var accountRepository: AccountRepository) : AccountService {
    override fun createAccount(marketName: String, images: List<String>): Account {
        val account = Account(marketName = marketName, images = images)
        return accountRepository.insert(account)
    }

    override fun getAccount(name: String): Account {
        val findByQuery = accountRepository.findByQuery(Query(Criteria.where("marketName").`is`(name)))
        if (findByQuery.size != 1) {
            throw NotFoundException("can not found account by marketName: $name")
        }
        return findByQuery.first()
    }
}