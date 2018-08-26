package net.sergey.kosov.authservice

import net.sergey.kosov.authservice.configurations.CustomSavedRequestAwareAuthenticationSuccessHandler
import net.sergey.kosov.authservice.configurations.extractors.OAuth2UserService
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer

@EnableDiscoveryClient
@EnableOAuth2Client
@EnableConfigurationProperties
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableResourceServer
class Configuration {

    @Bean
    OAuth2UserService oAuth2UserService() {
        return new OAuth2UserService()
    }

    @Bean
    FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
        def registration = new FilterRegistrationBean()
        registration.filter = filter
        registration.order = -100
        return registration
    }


    @Bean
    CustomSavedRequestAwareAuthenticationSuccessHandler customSavedRequestAwareAuthenticationSuccessHandler() {
        def handler = new CustomSavedRequestAwareAuthenticationSuccessHandler()
        handler.setDefaultTargetUrl("http://localhost:4000/view/products")
        handler.setAlwaysUseDefaultTargetUrl(true)
        return handler
    }
}
