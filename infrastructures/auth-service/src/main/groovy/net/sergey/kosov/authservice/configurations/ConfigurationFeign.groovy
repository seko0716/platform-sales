package net.sergey.kosov.authservice.configurations

import feign.RequestInterceptor
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.cloud.netflix.feign.EnableFeignClients
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails

@Configuration
@EnableFeignClients("net.sergey.kosov.authservice.api")
class ConfigurationFeign {
    @Bean
    @ConfigurationProperties(prefix = "security.oauth2.client")
    ClientCredentialsResourceDetails clientCredentialsResourceDetails() {
        return new ClientCredentialsResourceDetails()
    }

    @Bean
    RequestInterceptor oauth2FeignRequestInterceptor() {
        return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), clientCredentialsResourceDetails())
    }
}