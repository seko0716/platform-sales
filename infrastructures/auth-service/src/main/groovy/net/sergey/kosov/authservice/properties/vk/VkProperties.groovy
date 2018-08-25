package net.sergey.kosov.authservice.properties.vk

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "vk")
class VkProperties {
    String loginUrl = "/connect/vk"
    List<String> defaultRoles = ["DEFAULT_ROLE"]
    String loginField = "first_name"
    String emailField = "email"
    String idField = "id"
    String firstNameField = "first_name"
    String lastNameField = "last_name"
}
