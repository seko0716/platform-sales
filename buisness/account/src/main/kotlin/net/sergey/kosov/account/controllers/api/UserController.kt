package net.sergey.kosov.account.controllers.api

import net.sergey.kosov.account.domains.User
import net.sergey.kosov.account.services.UserService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(var userService: UserService) {
    @GetMapping(path = ["/user/{email:.+}"], consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun getUser(@PathVariable("email") email: String): User {
        return userService.getUser(email)
    }
}