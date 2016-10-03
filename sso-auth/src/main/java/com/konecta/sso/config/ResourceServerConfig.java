package com.konecta.sso.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        /*
         * para poder acceder a los recursos en UI web
         */
        resources.stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        /*
         * para poder acceder a los recursos en UI
         */
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

        /*
         * limitamos el alcance a los recursos para compartir
         */
        http.requestMatchers().antMatchers("/me");

        /*
         * configura los permisos para cada recurso
         */
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry autreq = http
                .authorizeRequests();
        autreq.antMatchers("/me").access("#oauth2.isOAuth()");
    }

}