package net.sergey.kosov.account.services

import net.sergey.kosov.account.domains.Account
import net.sergey.kosov.account.domains.ViewCreationAccount

interface AccountService {
    fun getAccount(name: String): Account
    fun createAccount(viewCreationAccount: ViewCreationAccount): Account
}