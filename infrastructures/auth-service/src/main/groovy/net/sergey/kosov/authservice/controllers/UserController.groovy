package net.sergey.kosov.authservice.controllers

import net.sergey.kosov.authservice.domains.User
import net.sergey.kosov.authservice.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

import javax.validation.Valid
import java.security.Principal

@RestController
@RequestMapping("/users")
class UserController {

    @Autowired
    private UserService userService

    @GetMapping(value = "/current")
    Principal getUser(Principal principal) {
        return principal
    }

    @GetMapping("/token/{protocol}")
    @PreAuthorize("#oauth2.hasScope('server')")
    String getToken(@PathVariable("protocol") String protocol) {
        return null
    }

    @PostMapping
    @PreAuthorize("#oauth2.hasScope('server')")
    void createUser(@Valid @RequestBody User user) {
        userService.create(user)
    }
}