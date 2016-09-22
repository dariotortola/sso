package com.konecta.sso.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;

import com.konecta.sso.service.UserClientAuthoritiesService;

/**
 * Registra en base de datos el login de un usuario por parte de un cliente
 * 
 * @author dtortola
 *
 */
@Component
@ConfigurationProperties(prefix = "konecta.security.login-listener")
public class AuthenticationListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private static final String INSERT = "insert into login(usuario, aplicacion, fecha) select u.id as usuario, a.id as aplicacion, ? as fecha from usuarios u, aplicaciones a where u.username = ? and a.codigo = ?";
    private static final String UPDATE = "update login set fecha = ? where usuario = (select min(u.id) from usuarios u where u.username = ?) and login.APLICACION = (select min(a.id) from aplicaciones a where a.CODIGO = ?)";

    @Autowired
    private JdbcTemplate jdbc;
    @Autowired
    private UserClientAuthoritiesService service;

    @Value("${solo-con-authorities:true}")
    private boolean soloConAuthorities;

    @Value("${solo-ultimo:true}")
    private boolean soloUltimoLogin;

    /**
     * Guarda el último login del usuario en la aplicación
     * 
     * Como se admite el login haya permisos o no, es posible que sea
     * conveniente comprobar la existencia de permisos antes de logar y, o bien
     * logar sólo si tiene permisos, o bien añadir la información de cuántos
     * permisos tiene
     * 
     * @param auth
     * @param timestamp
     */
    private void logOAuthLogin(OAuth2Authentication auth, long timestamp) {
        String username = auth.getName();
        String clientId = auth.getOAuth2Request().getClientId();
        Date fecha = new Date(timestamp);

        if (soloConAuthorities && service.getAuthorities(auth).isEmpty()) {
            // logamos sólo cuando hay authorities
            return;
        }

        if (!soloUltimoLogin || jdbc.update(UPDATE, fecha, username, clientId) < 1) {
            /*
             * sólo hacemos insert cuando tenemos que ponerlos todos
             * (!soloUltimoLogin) o cuando el update no ha encontrado ningún
             * registro
             */
            jdbc.update(INSERT, fecha, username, clientId);
        }
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        Authentication auth = event.getAuthentication();
        if (auth instanceof OAuth2Authentication) {
            logOAuthLogin((OAuth2Authentication) auth, event.getTimestamp());
        }
    }

}
