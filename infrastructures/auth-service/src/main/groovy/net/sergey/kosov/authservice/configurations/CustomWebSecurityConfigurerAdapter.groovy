package net.sergey.kosov.authservice.configurations

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.stereotype.Component
import org.springframework.web.filter.CompositeFilter

import javax.servlet.Filter

@Component
class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Autowired(required = false)
    List<OAuth2ClientAuthenticationProcessingFilter> oAuth2ClientAuthenticationProcessingFilters


    private Filter ssoFilter() {
        def filter = new CompositeFilter()
        filter.setFilters(oAuth2ClientAuthenticationProcessingFilters)

        return filter
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.antMatcher("/**").authorizeRequests().antMatchers("/", "/login**", "/webjars/**").permitAll().anyRequest()
                .authenticated().and().exceptionHandling()
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/")).and().logout()
                .logoutSuccessUrl("/").permitAll().and()
                .csrf().disable()
                .addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class)
        // @formatter:on
    }
}
