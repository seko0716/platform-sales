package net.sergey.kosov.authservice

import net.sergey.kosov.authservice.configurations.extractors.OAuth2UserService
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client

@EnableDiscoveryClient
@EnableOAuth2Client
@EnableConfigurationProperties
@EnableGlobalMethodSecurity(prePostEnabled = true)
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

}
