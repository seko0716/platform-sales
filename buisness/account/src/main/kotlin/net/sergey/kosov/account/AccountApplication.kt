package net.sergey.kosov.account

import net.sergey.kosov.account.domains.Account
import net.sergey.kosov.account.domains.User
import net.sergey.kosov.account.services.AccountService
import net.sergey.kosov.account.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import javax.annotation.PostConstruct

@EnableGlobalMethodSecurity(prePostEnabled = true)
@SpringBootApplication
class AccountApplication {
    @Autowired
    lateinit var accountService: AccountService
    @Autowired
    lateinit var userService: UserService

    @PostConstruct
    fun init() {
        var account: Account = accountService.createAccount(marketName = "test", images = listOf("http://placehold.it/800x300",
                "http://placehold.it/800x300",
                "http://placehold.it/800x300",
                "http://placehold.it/800x300"))

        userService.createUser(User(name = "user", family = "12", account = account))
    }

}

fun main(args: Array<String>) {
    SpringApplication.run(AccountApplication::class.java, *args)
}