package net.sergey.kosov.common.security


import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor
import org.springframework.boot.autoconfigure.security.oauth2.resource.FixedAuthoritiesExtractor
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.oauth2.client.OAuth2RestOperations
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.OAuth2Request
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices
import java.util.*

/**
 * Extended implementation of [org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices]
 *
 * By default, it designed to return only user details. This class provides [.getRequest] method, which
 * returns clientId and scope of calling service. This information used in controller's security checks.
 */

class CustomUserInfoTokenServices(private val userInfoEndpointUrl: String, private val clientId: String) : ResourceServerTokenServices {
    private val log = LoggerFactory.getLogger(javaClass)

    private val restTemplate: OAuth2RestOperations by lazy {
        val resource = BaseOAuth2ProtectedResourceDetails()
        resource.clientId = this.clientId
        OAuth2RestTemplate(resource)
    }

    private var tokenType = DefaultOAuth2AccessToken.BEARER_TYPE
    private var authoritiesExtractor: AuthoritiesExtractor = FixedAuthoritiesExtractor()

    @Throws(AuthenticationException::class, InvalidTokenException::class)
    override fun loadAuthentication(accessToken: String): OAuth2Authentication {
        val map = getMap(this.userInfoEndpointUrl, accessToken)
        if (map.containsKey("error")) {
            this.log.debug("userinfo returned error: " + map["error"])
            throw InvalidTokenException(accessToken)
        }
        return extractAuthentication(map)
    }

    private fun extractAuthentication(map: Map<String, Any>): OAuth2Authentication {
        val principal = getPrincipal(map)
        val request = getRequest(map)
        val authorities = authoritiesExtractor.extractAuthorities(map)
        val token = UsernamePasswordAuthenticationToken(
                principal, "N/A", authorities)
        token.details = map
        return OAuth2Authentication(request, token)
    }

    private fun getPrincipal(map: Map<String, Any>): Any? {
        PRINCIPAL_KEYS.forEach {
            if (map.containsKey(it)) {
                return map[it]
            }
        }
        return "unknown"
    }

    @Suppress("UNCHECKED_CAST")
    private fun getRequest(map: Map<String, Any>): OAuth2Request {
        val request = map["oauth2Request"] as Map<String, Any>

        val clientId = request["clientId"] as String
        val scope = LinkedHashSet(if (request.containsKey("scope"))
            setOf((request["scope"] as Map<String, String>)["scope"])
        else
            emptySet())

        return OAuth2Request(null, clientId, null, true, HashSet(scope), null, null, null, null)
    }

    override fun readAccessToken(accessToken: String) = throw UnsupportedOperationException("Not supported: read access token")

    private fun getMap(path: String, accessToken: String): Map<String, Any> {
        this.log.debug("Getting user info from: {}", path)
        try {
            val existingToken = restTemplate.oAuth2ClientContext.accessToken
            if (existingToken == null || accessToken != existingToken.value) {
                val token = DefaultOAuth2AccessToken(accessToken).also { it.tokenType = tokenType }
                restTemplate.oAuth2ClientContext.accessToken = token
            }
            @Suppress("UNCHECKED_CAST")
            val map = restTemplate.getForEntity(path, Map::class.java).body as MutableMap<String, Any>
            if (map["authorities"] == null) {
                map.remove("authorities")
            }
            return map
        } catch (ex: Exception) {
            this.log.info("Could not fetch user details: {}, {}", ex.javaClass, ex.message)
            return Collections.singletonMap<String, Any>("error", "Could not fetch user details")
        }

    }

    companion object {
        private val PRINCIPAL_KEYS = arrayOf("user", "username", "userid", "user_id", "login", "id", "name")
    }
}