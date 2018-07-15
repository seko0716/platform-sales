package net.sergey.kosov.account.services

import net.sergey.kosov.account.api.AuthUserClient
import net.sergey.kosov.account.domains.User
import net.sergey.kosov.account.repositories.UserRepository
import net.sergey.kosov.common.exceptions.NotFoundException
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service

@Service
class AccountUserService(var userRepository: UserRepository,
                         var authUserClient: AuthUserClient) : UserService {

    override fun getUsersInAccount(name: String): List<User> {
        val account = getUser(name).account
        return userRepository.findByQuery(Query.query(Criteria.where("account").`is`(account)))
    }

    override fun createUser(user: User, password: String): User {
        val authUser = User.User(username = user.email, password = password)
        authUserClient.createUser(authUser)
        return userRepository.insert(user)
    }

    override fun getUser(email: String): User {
        val findByQuery = userRepository.findByQuery(Query(Criteria.where("email").`is`(email)))
        if (findByQuery.size != 1) {
            throw NotFoundException("can not found user by email: $email")
        }
        return findByQuery.first()
    }
}

