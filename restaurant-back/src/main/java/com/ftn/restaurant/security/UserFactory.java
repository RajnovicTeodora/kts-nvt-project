package com.ftn.restaurant.security;

import com.ftn.restaurant.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;

public class UserFactory {

    public static SecurityUser create(User user) {
        Collection<? extends GrantedAuthority> authorities;
        try {
            authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_" +user.getRole().getName());
        } catch (Exception e) {
            authorities = null;
        }

        return new SecurityUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
            authorities
        );
    }


}
