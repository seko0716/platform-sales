package net.sergey.kosov.authservice.configurations.vk

import groovy.util.logging.Slf4j
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider
import org.springframework.util.MultiValueMap

@Slf4j
class VkAuthorizationCodeAccessTokenProvider extends AuthorizationCodeAccessTokenProvider {
    String getAccessTokenUri(OAuth2ProtectedResourceDetails resource, MultiValueMap<String, String> form) {
        def accessTokenUri = resource.accessTokenUri

        if (logger.isDebugEnabled()) {
            logger.debug("Retrieving token from $accessTokenUri")
        }

        def separator = "?"
        if (accessTokenUri.contains("?")) {
            separator = "&"
        }

        def builder = new StringBuilder(accessTokenUri)
        for (key in form.keys) {
            builder.append(separator)
            builder.append(key).append("={").append(key).append("}")
            separator = "&"
        }

        return builder.toString()
    }
}