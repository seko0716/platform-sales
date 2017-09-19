package net.sergey.kosov.authservice.repository

import net.sergey.kosov.authservice.domains.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository extends CrudRepository<User, String> {

}