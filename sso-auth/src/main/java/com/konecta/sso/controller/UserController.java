package com.konecta.sso.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.konecta.sso.service.UserClientAuthoritiesService;

/**
 * Endpoints de usuario a efectos de autenticación, etc
 * 
 * @author dtortola
 *
 */
@RestController
public class UserController {

    @Autowired
    private UserClientAuthoritiesService service;

    /**
     * @param logged
     * @return información del usuario logado actualmente
     */
    @GetMapping({ "me", "user/me" })
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
