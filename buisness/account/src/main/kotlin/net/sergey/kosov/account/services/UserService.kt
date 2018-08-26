package net.sergey.kosov.account.services

import net.sergey.kosov.account.domains.User
import net.sergey.kosov.account.domains.ViewCreationAccount

interface UserService {

    fun getUser(email: String): User
    fun createUser(user: User, password: String, social: Boolean): User
    fun getUsersInAccount(name: String): List<User>
    fun updateUser(name: String, viewCreationAccount: ViewCreationAccount): User
}