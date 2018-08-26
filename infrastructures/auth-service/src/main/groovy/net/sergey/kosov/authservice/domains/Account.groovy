package net.sergey.kosov.authservice.domains


class Account {
    String marketName
    String description = ""
    String linkToAddedUsers = UUID.randomUUID().toString()
}

