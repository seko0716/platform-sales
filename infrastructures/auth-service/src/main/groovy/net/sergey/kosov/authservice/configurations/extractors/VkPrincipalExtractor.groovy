package net.sergey.kosov.authservice.configurations.extractors


import groovy.util.logging.Slf4j
import net.sergey.kosov.authservice.api.AccountApi
import net.sergey.kosov.authservice.domains.User
import net.sergey.kosov.authservice.domains.ViewCreationAccount
import net.sergey.kosov.authservice.properties.vk.VkProperties
import net.sergey.kosov.authservice.repository.UserRepository
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor

@Slf4j
class VkPrincipalExtractor implements PrincipalExtractor {
    UserRepository userStorage
    VkProperties vk
    OAuth2UserService oAuth2UserService
    AccountApi accountApi

    VkPrincipalExtractor(UserRepository userStorage, VkProperties vk, OAuth2UserService oAuth2UserService, AccountApi accountApi) {
        this.userStorage = userStorage
        this.vk = vk
        this.oAuth2UserService = oAuth2UserService
        this.accountApi = accountApi
    }
    private def authServiceType = "VK"

    Object extractPrincipal(Map<String, Object> map) {
        log.trace("credential map: {}", map)
        map["_authServiceType"] = authServiceType
        def result = oAuth2UserService.getDetails(map)
        def socialAccountId = result[vk.idField]
        def user = userStorage.findOneBySocialAccountId(socialAccountId)

        if (user == null) {
            log.debug("user with social account id {} not found", socialAccountId)
            def userT = new User(result[vk.loginField],
                    "pass",
                    socialAccountId)
            userT = userStorage.save(userT)

            createAccount(result)

            log.debug("user be created {}", userT)
            user = userT
        }

        log.trace("user with social account id {} exist {}", socialAccountId, user)
        return user
    }


    private void createAccount(Map<String, String> user) {
        def creationAccount = ViewCreationAccount.builder()
                .email(user[vk.loginField])
                .firstName(user[vk.firstNameField])
                .lastName(user[vk.lastNameField])
                .fullName(user[vk.firstNameField] + " " + user[vk.lastNameField])
                .password("pass")
                .marketName(user[vk.loginField]).build()

        accountApi.createAccountSocial(creationAccount)
    }
}