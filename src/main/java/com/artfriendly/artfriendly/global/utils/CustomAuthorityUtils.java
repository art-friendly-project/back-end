package com.artfriendly.artfriendly.global.utils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomAuthorityUtils {

    private final List<String> USER_ROLES_STRING = List.of("USER");
    private final List<String> ADMIN_ROLES_STRING = List.of("ADMIN");

    public List<String> createUserRoles() {
        return USER_ROLES_STRING;
    }

    public List<String> createAdminRoles() {
        return ADMIN_ROLES_STRING;
    }

    public List<GrantedAuthority> createAuthorities(List<String> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }
}
