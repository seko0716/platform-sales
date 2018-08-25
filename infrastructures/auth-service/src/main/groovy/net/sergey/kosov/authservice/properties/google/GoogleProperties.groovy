package net.sergey.kosov.authservice.properties.google

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "google")
class GoogleProperties {
    String loginUrl = "/connect/google"
    List<String> defaultRoles = ["DEFAULT_ROLE"]
    String loginField = "email"
    String emailField = "email"
    String idField = "sub"
    String firstNameField = "given_name"
    String lastNameField = "family_name"
}