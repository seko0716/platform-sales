package net.sergey.kosov.authservice.configurations.extractors

import groovy.util.logging.Slf4j
import net.sergey.kosov.authservice.domains.User
import net.sergey.kosov.authservice.properties.google.GoogleProperties
import net.sergey.kosov.authservice.repository.UserRepository
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor

@Slf4j
class GooglePrincipalExtractor implements PrincipalExtractor {
    private def authServiceType = "GOOGLE"

    UserRepository userStorage
    GoogleProperties google
    OAuth2UserService oAuth2UserService

    GooglePrincipalExtractor(UserRepository userStorage, GoogleProperties google, OAuth2UserService oAuth2UserService) {
        this.userStorage = userStorage
        this.google = google
        this.oAuth2UserService = oAuth2UserService
    }

    @Override
    Object extractPrincipal(Map<String, Object> map) {
        log.trace("credential map: {}", map)
        map["_authServiceType"] = authServiceType
        def result = oAuth2UserService.getDetails(map)
        def socialAccountId = result[google.idField]
        def user = userStorage
                .findOneBySocialAccountId(socialAccountId)

        if (!user) {
            log.debug("user with social account id {} not found", socialAccountId)
            def userT = new User(result[google.loginField],
                    "pass",
                    socialAccountId)
            userT = userStorage.save(userT)
            log.debug("user be created {}", userT)
            user = userT
        }
        log.trace("user with social account id {} exist {}", socialAccountId, user)
        return user
    }
}