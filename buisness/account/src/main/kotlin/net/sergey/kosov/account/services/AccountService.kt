package net.sergey.kosov.account.services

import net.sergey.kosov.account.domains.Account

interface AccountService {
    fun getAccount(name: String): Account
    fun createAccount(marketName: String, images: List<String>): Account
}