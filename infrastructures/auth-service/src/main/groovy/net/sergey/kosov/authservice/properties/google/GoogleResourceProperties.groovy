package net.sergey.kosov.authservice.properties.google

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken

@ConfigurationProperties("google.resource")
class GoogleResourceProperties {

    @JsonIgnore
    final String clientId

    @JsonIgnore
    final String clientSecret

    String serviceId = "resource"

    /**
     * Identifier of the resource.
     */
    String id

    /**
     * URI of the user endpoint.
     */
    String userInfoUri

    /**
     * URI of the token decoding endpoint.
     */
    String tokenInfoUri

    /**
     * Use the token info, can be set to false to use the user info.
     */
    boolean preferTokenInfo = true

    /**
     * The token type to send when using the userInfoUri.
     */
    String tokenType = DefaultOAuth2AccessToken.BEARER_TYPE

    int filterOrder = SecurityProperties.ACCESS_OVERRIDE_ORDER - 1

}