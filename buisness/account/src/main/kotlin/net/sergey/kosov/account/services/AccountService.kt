package net.sergey.kosov.account.services

import net.sergey.kosov.account.domains.Account
import net.sergey.kosov.account.domains.ViewCreationAccount
import net.sergey.kosov.account.domains.ViewUpdateAccount

interface AccountService {
    fun getAccount(marketName: String): Account
    fun getAccount(userName: String, accountId: String): Account
    fun createAccount(viewCreationAccount: ViewCreationAccount, social: Boolean): Account
    fun getAccountByUser(name: String): Account
    fun addUser(name: String, marketName: String, uuid: String): Account
    fun updateAccount(name: String, viewUpdateAccount: ViewUpdateAccount): Account
    fun createAccountSocial(viewCreationAccount: ViewCreationAccount): Account
}