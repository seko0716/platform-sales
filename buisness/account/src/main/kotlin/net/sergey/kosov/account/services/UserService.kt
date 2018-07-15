package net.sergey.kosov.account.services

import net.sergey.kosov.account.domains.User

interface UserService {

    fun getUser(email: String): User
    fun createUser(user: User, password: String): User
    fun getUsersInAccount(name: String): List<User>
}