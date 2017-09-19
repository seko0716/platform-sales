package net.sergey.kosov.authservice.services.security

import net.sergey.kosov.authservice.domains.User
import net.sergey.kosov.authservice.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class MongoUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findOne(username)
        if (user == null) {
            throw new UsernameNotFoundException(username)
        }
        return user
    }
}