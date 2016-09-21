package com.konecta.sso.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.konecta.sso.service.UserClientAuthoritiesService;

@RestController
public class UserController {

    @Autowired
    private UserClientAuthoritiesService service;


    @RequestMapping("resource/me")
    public Authentication getMe(OAuth2Authentication logged) {
        List<String> authorities = service.getAuthorities(logged);
        return new KonectaAuthentication(logged, authorities);
    }

}
