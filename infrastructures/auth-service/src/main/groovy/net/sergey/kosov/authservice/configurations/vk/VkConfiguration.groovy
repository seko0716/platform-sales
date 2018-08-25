package net.sergey.kosov.authservice.configurations.vk

import groovy.util.logging.Slf4j
import net.sergey.kosov.authservice.configurations.extractors.AuthoritiesExtractorImpl
import net.sergey.kosov.authservice.configurations.extractors.OAuth2UserService
import net.sergey.kosov.authservice.configurations.extractors.VkPrincipalExtractor
import net.sergey.kosov.authservice.properties.vk.VkClientProperty
import net.sergey.kosov.authservice.properties.vk.VkProperties
import net.sergey.kosov.authservice.properties.vk.VkResourceProperties
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
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitAccessTokenProvider
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider

@Configuration
@EnableConfigurationProperties([VkClientProperty.class, VkProperties.class, VkResourceProperties.class])
@Slf4j
class VkConfiguration {
    UserRepository userStorage
    OAuth2ClientContext oauth2ClientContext
    OAuth2UserService oAuth2UserService

    VkConfiguration(UserRepository userStorage, OAuth2ClientContext oauth2ClientContext, OAuth2UserService oAuth2UserService) {
        this.userStorage = userStorage
        this.oauth2ClientContext = oauth2ClientContext
        this.oAuth2UserService = oAuth2UserService
    }

    @Bean
    OAuth2ClientAuthenticationProcessingFilter vkFilter(VkResourceProperties vkResource, VkClientProperty vkClient) {
        def vkFilter = new OAuth2ClientAuthenticationProcessingFilter(vk().loginUrl)
        def vkTemplate = new OAuth2RestTemplate(vkClient, oauth2ClientContext)
        vkFilter.setRestTemplate(vkTemplate)
        vkTemplate.setAccessTokenProvider(new AccessTokenProviderChain(Arrays.asList(
                vkTokenProvider(), new ImplicitAccessTokenProvider(),
                new ResourceOwnerPasswordAccessTokenProvider(), new ClientCredentialsAccessTokenProvider())))
        def tokenServices = new UserInfoTokenServices(vkResource.userInfoUri, vkClient.clientId)
        tokenServices.setRestTemplate(vkTemplate)
        tokenServices.setTokenType("code")
        tokenServices.setAuthoritiesExtractor(vkAuthoritiesExtractor())
        tokenServices.setPrincipalExtractor(vkPrincipalExtractor())
        vkFilter.setTokenServices(tokenServices)
        log.trace("init google oauth2 filter")
        return vkFilter
    }

    @Bean("vkClient")
    @ConfigurationProperties("vk.client")
    VkClientProperty vkClient() {
        return new VkClientProperty()
    }

    @Bean("vkResource")
    @ConfigurationProperties("vk.resource")
    VkResourceProperties vkResource() {
        return new VkResourceProperties()
    }

    @Bean("vk")
    @ConfigurationProperties("vk")
    VkProperties vk() {
        return new VkProperties()
    }

    @Bean
    VkPrincipalExtractor vkPrincipalExtractor() {
        return new VkPrincipalExtractor(userStorage, vk(), oAuth2UserService)
    }

    @Bean("vkTokenProvider")
    AuthorizationCodeAccessTokenProvider vkTokenProvider() {
        return new VkAuthorizationCodeAccessTokenProvider()
    }

    @Bean
    AuthoritiesExtractor vkAuthoritiesExtractor() {
        return new AuthoritiesExtractorImpl(userStorage, oAuth2UserService, vk().idField)
    }
}