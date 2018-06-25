package net.sergey.kosov.account.services

import net.sergey.kosov.account.domains.User

interface UserService {

    fun getUser(username: String): User
}