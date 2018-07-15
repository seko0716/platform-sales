package net.sergey.kosov.account.controllers.api

import net.sergey.kosov.account.domains.User
import net.sergey.kosov.account.services.UserService
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
class UserController(var userService: UserService) {

    @GetMapping(path = ["/user/{email:.+}"], consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    @PreAuthorize("#oauth2.hasScope('server')")
    fun getUser(@PathVariable("email") email: String): User {
        return userService.getUser(email)
    }

    @GetMapping(path = ["/current/user"], consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun getCurrentUser(principal: Principal): User {
        return userService.getUser(principal.name)
    }

    @GetMapping(path = ["/users"], consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun getUsersInAccount(principal: Principal): List<String> {
        return userService.getUser(principal.name).account.users
    }

}