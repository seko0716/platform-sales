package net.sergey.kosov.authservice.configurations

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.web.filter.CompositeFilter

import javax.servlet.Filter

@Configuration
@EnableResourceServer
@EnableWebSecurity
class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired(required = false)
    List<OAuth2ClientAuthenticationProcessingFilter> oAuth2ClientAuthenticationProcessingFilters

    private Filter ssoFilter() {
        def filter = new CompositeFilter()
        filter.setFilters(oAuth2ClientAuthenticationProcessingFilters)
        return filter
    }


    @Autowired
    void configureGlobal(AuthenticationManagerBuilder auth, UserDetailsService userDetailsService) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder())
    }

    @Override
    void configure(HttpSecurity http) throws Exception {
        http

                .authorizeRequests()
                .anyRequest().authenticated().and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .csrf().disable()
                .addFilterAfter(ssoFilter(), BasicAuthenticationFilter.class)
    }

}