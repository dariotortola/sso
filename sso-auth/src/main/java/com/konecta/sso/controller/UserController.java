package com.konecta.sso.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.konecta.sso.service.UserClientAuthoritiesService;

@RestController
public class UserController {

    @Autowired
    private UserClientAuthoritiesService service;

    @RequestMapping({ "me", "user/me" })
    // @PreAuthorize("#oauth2.isOauth() or isAuthenticated()")
    public Authentication getMe(Authentication logged) {
        if (logged instanceof OAuth2Authentication) {
            /* entrada por OAuth, filtramos los permisos por aplicación */
            List<String> authorities = service.getAuthorities((OAuth2Authentication) logged);
            return new KonectaAuthentication(logged, authorities);
        } else {
            return logged;
        }
    }

}
