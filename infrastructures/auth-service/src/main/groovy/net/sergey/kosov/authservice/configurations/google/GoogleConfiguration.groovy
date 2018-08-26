package net.sergey.kosov.authservice.configurations.google

import groovy.util.logging.Slf4j
import net.sergey.kosov.authservice.configurations.CustomSavedRequestAwareAuthenticationSuccessHandler
import net.sergey.kosov.authservice.configurations.extractors.AuthoritiesExtractorImpl
import net.sergey.kosov.authservice.configurations.extractors.GooglePrincipalExtractor
import net.sergey.kosov.authservice.configurations.extractors.OAuth2UserService
import net.sergey.kosov.authservice.properties.google.GoogleClientProperty
import net.sergey.kosov.authservice.properties.google.GoogleProperties
import net.sergey.kosov.authservice.properties.google.GoogleResourceProperties
import net.sergey.kosov.authservice.repository.UserRepository
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.client.OAuth2ClientContext
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter

@Configuration
@EnableConfigurationProperties(value = [
        GoogleClientProperty.class,
        GoogleProperties.class,
        GoogleResourceProperties.class
])
@Slf4j
class GoogleConfiguration {
    UserRepository userStorage
    OAuth2ClientContext oauth2ClientContext
    OAuth2UserService oAuth2UserService
    CustomSavedRequestAwareAuthenticationSuccessHandler customSavedRequestAwareAuthenticationSuccessHandler

    GoogleConfiguration(UserRepository userStorage, OAuth2ClientContext oauth2ClientContext, OAuth2UserService oAuth2UserService, CustomSavedRequestAwareAuthenticationSuccessHandler customSavedRequestAwareAuthenticationSuccessHandler) {
        this.userStorage = userStorage
        this.oauth2ClientContext = oauth2ClientContext
        this.oAuth2UserService = oAuth2UserService
        this.customSavedRequestAwareAuthenticationSuccessHandler = customSavedRequestAwareAuthenticationSuccessHandler
    }

    @Bean
    OAuth2ClientAuthenticationProcessingFilter googleFilter(GoogleResourceProperties googleResource,
                                                            GoogleClientProperty googleClient) {
        def googleFilter = new OAuth2ClientAuthenticationProcessingFilter(google().loginUrl)
        googleFilter.setAuthenticationSuccessHandler(customSavedRequestAwareAuthenticationSuccessHandler)
        def googleTemplate = new OAuth2RestTemplate(googleClient, oauth2ClientContext)
        googleFilter.setRestTemplate(googleTemplate)
        def tokenServices = new UserInfoTokenServices(googleResource.userInfoUri, googleClient.clientId)
        tokenServices.setRestTemplate(googleTemplate)
        tokenServices.setAuthoritiesExtractor(googleAuthoritiesExtractor())
        tokenServices.setPrincipalExtractor(googlePrincipalExtractor())
        googleFilter.setTokenServices(tokenServices)
        log.trace("init google oauth2 filter")
        return googleFilter
    }

    @Bean("googleClient")
    @ConfigurationProperties("google.client")
    GoogleClientProperty googleClient() {
        return new GoogleClientProperty()
    }

    @Bean("googleResource")
    @ConfigurationProperties("google.resource")
    GoogleResourceProperties googleResource() {
        return new GoogleResourceProperties()
    }

    @Bean("google")
    @ConfigurationProperties("google")
    GoogleProperties google() {
        return new GoogleProperties()
    }

    @Bean("googlePrincipalExtractor")
    GooglePrincipalExtractor googlePrincipalExtractor() {
        return new GooglePrincipalExtractor(userStorage, google(), oAuth2UserService)
    }

    @Bean
    AuthoritiesExtractor googleAuthoritiesExtractor() {
        return new AuthoritiesExtractorImpl(userStorage, oAuth2UserService, google().idField)
    }
}