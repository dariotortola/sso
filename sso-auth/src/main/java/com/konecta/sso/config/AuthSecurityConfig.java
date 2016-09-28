package com.konecta.sso.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * Configuración de seguridad por la parte de aplicación
 * 
 * @author dtortola
 *
 */
@Configuration
@EnableWebSecurity
public class AuthSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authReq = http
                .authorizeRequests();
        authReq.antMatchers("/", "/login**", "/usuario/**").permitAll();
        authReq.anyRequest().authenticated();

        http.exceptionHandling().accessDeniedHandler(new AccessDeniedHandlerImpl());

        http.formLogin().loginPage("/login").permitAll();
        http.rememberMe().userDetailsService(userDetailsService).key("AccesoKonecta");
        http.logout().deleteCookies("JSESSIONID", "XSRF-TOKEN", "remember-me");
        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        /*
         * para poder compartirlo como bean en la configuración del servidor de
         * autorización
         */
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // pasamos de los elementos estáticos
        web.ignoring().antMatchers("/webjars/**", "/js/**", "/html/**");
    }

}
