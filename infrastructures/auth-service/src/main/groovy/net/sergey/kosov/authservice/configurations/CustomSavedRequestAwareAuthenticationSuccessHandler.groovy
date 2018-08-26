package net.sergey.kosov.authservice.configurations


import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler

import javax.servlet.ServletException
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomSavedRequestAwareAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        def cookie = new Cookie("token", (authentication.details as OAuth2AuthenticationDetails).tokenValue)
        cookie.httpOnly = false
        cookie.path = "/"
        response.addCookie(cookie)
        super.onAuthenticationSuccess(request, response, authentication)
    }
}