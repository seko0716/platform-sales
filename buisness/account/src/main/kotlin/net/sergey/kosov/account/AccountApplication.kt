package net.sergey.kosov.account

import feign.RequestInterceptor
import net.sergey.kosov.account.domains.Account
import net.sergey.kosov.account.domains.Gender
import net.sergey.kosov.account.domains.User
import net.sergey.kosov.account.repositories.AccountRepository
import net.sergey.kosov.account.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import java.time.LocalDate
import javax.annotation.PostConstruct

@EnableOAuth2Client
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
@SpringBootApplication
class AccountApplication {
    @Autowired
    lateinit var accountService: AccountRepository
    @Autowired
    lateinit var userService: UserRepository

    @PostConstruct
    fun init() {
        try {
            accountService.save(Account(marketName = "test", description = ""))
            val save = accountService.save(Account(marketName = "admin", description = ""))
            userService.save(User(fullName = "fun", firstName = "fn", lastName = "ln", email = "admin", birthDay = LocalDate.now(), country = "", gender = Gender.FEMALE, account = save, password = ""))
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


}

fun main(args: Array<String>) {
    SpringApplication.run(AccountApplication::class.java, *args)
}

@Configuration
class CustomResourceServerConfigurerAdapter : ResourceServerConfigurerAdapter() {

    @Bean
    @ConfigurationProperties(prefix = "security.oauth2.client")
    fun clientCredentialsResourceDetails(): ClientCredentialsResourceDetails {
        return ClientCredentialsResourceDetails()
    }

    @Bean
    fun oauth2FeignRequestInterceptor(): RequestInterceptor {
        return OAuth2FeignRequestInterceptor(DefaultOAuth2ClientContext(), clientCredentialsResourceDetails())
    }

    @Bean
    fun clientCredentialsRestTemplate(): OAuth2RestTemplate {
        return OAuth2RestTemplate(clientCredentialsResourceDetails())
    }


    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
                .antMatchers("/products", "/product/*", "/products/market/*").permitAll()
                .anyRequest().authenticated()
    }
}