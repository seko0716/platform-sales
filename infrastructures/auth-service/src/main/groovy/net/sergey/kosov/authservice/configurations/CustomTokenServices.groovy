package net.sergey.kosov.authservice.configurations

import org.springframework.security.core.AuthenticationException
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.DefaultTokenServices

class CustomTokenServices extends DefaultTokenServices {
    List<OAuth2ClientAuthenticationProcessingFilter> oAuth2ClientAuthenticationProcessingFilters

    CustomTokenServices(List<OAuth2ClientAuthenticationProcessingFilter> oAuth2ClientAuthenticationProcessingFilters) {
        this.oAuth2ClientAuthenticationProcessingFilters = oAuth2ClientAuthenticationProcessingFilters
    }

    @Override
    OAuth2Authentication loadAuthentication(String accessTokenValue) throws AuthenticationException, InvalidTokenException {
        def tokenServices = oAuth2ClientAuthenticationProcessingFilters.collect { it.tokenServices }
        for (def tokenService : tokenServices) {
            try {
                return tokenService.loadAuthentication(accessTokenValue)
            } catch (Exception e) {
                e.printStackTrace()
            }
        }
        return super.loadAuthentication(accessTokenValue)
    }
}
