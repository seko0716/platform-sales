package net.sergey.kosov.account.repositories

import net.sergey.kosov.account.domains.User
import net.sergey.kosov.common.repositories.RepositoryQuery
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : MongoRepository<User, String>, RepositoryQuery<User>