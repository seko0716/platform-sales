package net.sergey.kosov.authservice.services

import net.sergey.kosov.authservice.domains.User

interface UserService {
    void create(User user)
}