package net.sergey.kosov.authservice.configurations.extractors

import groovy.util.logging.Slf4j
import net.sergey.kosov.authservice.repository.UserRepository
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor
import org.springframework.security.core.GrantedAuthority

@Slf4j
class AuthoritiesExtractorImpl implements AuthoritiesExtractor {
    UserRepository userStorage
    OAuth2UserService oAuth2UserService
    String idField

    AuthoritiesExtractorImpl(UserRepository userStorage, OAuth2UserService oAuth2UserService, String idField) {
        this.userStorage = userStorage
        this.oAuth2UserService = oAuth2UserService
        this.idField = idField
    }

    List<GrantedAuthority> extractAuthorities(Map<String, Object> map) {
        log.debug("credential map: {}", map)
        def socialAccountId = oAuth2UserService.getDetails(map)[idField]
        def user = userStorage.findOneBySocialAccountId(socialAccountId)
        if (user != null) {
//            return user.roles.toMutableList()
            return new ArrayList()
        }
        log.debug("user with social account id {} not found", socialAccountId)
        return new ArrayList()
    }

}