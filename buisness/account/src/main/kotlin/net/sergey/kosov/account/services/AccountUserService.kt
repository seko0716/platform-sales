package net.sergey.kosov.account.services

import net.sergey.kosov.account.api.AuthUserClient
import net.sergey.kosov.account.domains.User
import net.sergey.kosov.account.domains.ViewCreationAccount
import net.sergey.kosov.account.repositories.UserRepository
import net.sergey.kosov.common.exceptions.NotFoundException
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service

@Service
class AccountUserService(private var userRepository: UserRepository,
                         private var authUserClient: AuthUserClient) : UserService {
    override fun updateUser(name: String, viewCreationAccount: ViewCreationAccount): User {
        val user = getUser(name)
        user.apply {
            fullName = viewCreationAccount.fullName
            firstName = viewCreationAccount.firstName
            lastName = viewCreationAccount.lastName
            birthDay = viewCreationAccount.birthDay
            country = viewCreationAccount.country
            gender = viewCreationAccount.gender
        }

        return userRepository.save(user)
    }

    override fun getUsersInAccount(name: String): List<User> {
        val account = getUser(name).account
        return userRepository.findByQuery(Query.query(Criteria.where("account").`is`(account)))
    }

    override fun createUser(user: User, password: String, social: Boolean): User {
        val authUser = User.User(username = user.email, password = password)
        if (!social) {
            user.needUpdatePassword = false
            authUserClient.createUser(authUser)
        }
        return userRepository.insert(user)
    }

    override fun getUser(email: String): User {
        return userRepository.findOneByQuery(Query(Criteria.where("email").`is`(email)))
                ?: throw NotFoundException("can not found user by email: $email")
    }
}

