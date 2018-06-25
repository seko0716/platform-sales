package net.sergey.kosov.account.repositories

import net.sergey.kosov.account.domains.Account
import net.sergey.kosov.common.repositories.RepositoryQuery
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : MongoRepository<Account, String>, RepositoryQuery<Account>