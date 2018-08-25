package net.sergey.kosov.authservice.properties.google

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails

@ConfigurationProperties(prefix = "google.client")
class GoogleClientProperty extends AuthorizationCodeResourceDetails {

}

