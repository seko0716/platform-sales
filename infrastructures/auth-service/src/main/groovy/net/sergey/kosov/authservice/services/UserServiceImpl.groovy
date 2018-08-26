package net.sergey.kosov.authservice.services

import groovy.util.logging.Slf4j
import net.sergey.kosov.authservice.domains.User
import net.sergey.kosov.authservice.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.util.Assert

import javax.annotation.PostConstruct

@Service
@Slf4j
class UserServiceImpl implements UserService {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder()
    @Autowired
    private UserRepository repository

    @Override
    void create(User user) {
        User existing = repository.findOne(user.getUsername())
        Assert.isNull(existing, "user already exists: " + user.getUsername())
        String hash = encoder.encode(user.getPassword())
        user.setPassword(hash)
        repository.save(user)
        log.info("new user has been created: {}", user.getUsername())
    }

    @PostConstruct
    void init() {
        def user = new User("admin", "11", null)
        String hash = encoder.encode(user.getPassword())
        user.setPassword(hash)
        repository.save(user)
        user = new User("test", "11", null)
        hash = encoder.encode(user.getPassword())
        user.setPassword(hash)
        repository.save(user)
    }
}
