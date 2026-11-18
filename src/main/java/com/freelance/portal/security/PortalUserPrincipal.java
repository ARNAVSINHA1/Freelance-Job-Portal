package com.freelance.portal.security;

import com.freelance.portal.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class PortalUserPrincipal implements UserDetails {
    private final Long id;
    private final String email;
    private final String password;
    private final String role;

    public PortalUserPrincipal(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.role = user.getRole().name();
    }

    public Long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean equals(Object other) {
        return this == other
                || other instanceof PortalUserPrincipal principal
                && id.equals(principal.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
