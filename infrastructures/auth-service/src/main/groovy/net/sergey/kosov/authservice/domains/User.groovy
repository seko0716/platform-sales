package net.sergey.kosov.authservice.domains

import groovy.transform.Canonical
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Canonical
@Document(collection = "users")
class User implements UserDetails {
    User(String username, String password, String socialAccountId) {
        this.username = username
        this.password = password
        this.socialAccountId = socialAccountId
    }
    @Id
    String username
    String password
    String socialAccountId

    @Override
    List<GrantedAuthority> getAuthorities() {
        return null
    }

    @Override
    boolean isAccountNonExpired() {
        return true
    }

    @Override
    boolean isAccountNonLocked() {
        return true
    }

    @Override
    boolean isCredentialsNonExpired() {
        return true
    }

    @Override
    boolean isEnabled() {
        return true
    }
}