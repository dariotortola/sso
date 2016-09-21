package com.konecta.sso.controller;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class KonectaAuthentication implements Authentication {
    /**
     * 
     */
    private static final long serialVersionUID = -6748231992364823229L;
    private List<GrantedAuthority> authorities;
    private Authentication wrapped;

    public KonectaAuthentication(Authentication original, Collection<String> authorities) {
        wrapped = original;
        this.authorities = authorities.stream().map(s -> new SimpleGrantedAuthority(s)).collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return wrapped.getCredentials();
    }

    @Override
    public Object getDetails() {
        return wrapped.getDetails();
    }

    @Override
    public String getName() {
        return wrapped.getName();
    }

    @Override
    public Object getPrincipal() {
        return wrapped.getPrincipal();
    }

    @Override
    public boolean isAuthenticated() {
        return wrapped.isAuthenticated();
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        wrapped.setAuthenticated(isAuthenticated);
    }

    @Override
    public String toString() {
        return wrapped.toString();
    }

}
