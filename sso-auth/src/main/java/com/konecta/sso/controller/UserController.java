package com.konecta.sso.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.konecta.sso.service.UserClientAuthoritiesService;

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

    /**
     * Si el usuario no existe se devolverá un error
     * 
     * @param username
     */
    @PostMapping("user/forgot")
    public ForgottenResponse setForgotten(@RequestBody String username) {
        ForgottenResponse response = new ForgottenResponse();
        response.setError("No implementado");
        /*
         * TODO busca username en bbdd, por username, meta4 o email // si no se
         * encuentra, un error // si se encuentra, pero no tiene email, que
         * avise al supervisor // si se encuentra, envía email y return
         * ForgottenResponse vacío
         */
        return response;
    }

    public static class ForgottenResponse {
        private String error;

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public boolean isSuccess() {
            return StringUtils.isBlank(error);
        }
    }
}
