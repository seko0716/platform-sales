package net.sergey.kosov.account.controllers.api

import net.sergey.kosov.account.domains.User
import net.sergey.kosov.account.domains.ViewCreationAccount
import net.sergey.kosov.account.services.UserService
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
class UserController(var userService: UserService) {

    @GetMapping(path = ["/user/{email:.+}"])
//    @PreAuthorize("#oauth2.hasScope('server')")
    fun getUser(@PathVariable("email") email: String): User {
        return userService.getUser(email)
    }

    @GetMapping(path = ["/current/user"])
    fun getCurrentUser(principal: Principal): User {
        return userService.getUser(principal.name)
    }

    @GetMapping(path = ["/users"])
    fun getUsersInAccount(principal: Principal): List<String> {
        return userService.getUser(principal.name).account.users
    }

    @PostMapping(path = ["/user/update"])
    fun updateUser(principal: Principal, @RequestBody viewCreationAccount: ViewCreationAccount): User {
        return userService.updateUser(principal.name, viewCreationAccount)
    }

}