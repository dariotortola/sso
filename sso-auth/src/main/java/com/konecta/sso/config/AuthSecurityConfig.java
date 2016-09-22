package com.konecta.sso.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

/**
 * Configuración de seguridad por la parte de aplicación
 * 
 * @author dtortola
 *
 */
@Configuration
@EnableWebSecurity
public class AuthSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authReq = http
                .authorizeRequests();
        authReq.antMatchers("/", "/login**").permitAll();
        /*
         * en control de acceso, el permiso mínimo es existir en la base de
         * datos de usuarios
         */
        authReq.anyRequest().authenticated();

        http.exceptionHandling().accessDeniedHandler(new AccessDeniedHandlerImpl());

        http.formLogin().loginPage("/login").permitAll();
        /*
         * TODO logout que borre cookies, remember me
         */
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
        web.ignoring().antMatchers("/webjars/**", "/js/**");
    }

    /**
     * @return password encoder para seguridad
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        /*
         * Sólo con incluir un PasswordEncoder, Spring lo asigna automáticamente
         * al UserDetailsService
         */
        /*
         * TODO esto es un poco legacy. Como las actualizaciones en la base de
         * datos han sido hechas con métodos de DBCrypto y éste sólo tiene MD5,
         * SHA1 y MD4, hemos estado limitados a ello.
         * 
         * Podríamos cambiar esto para utilizar bcrypt, por ejemplo (o sha-256,
         * etc) y sólo tendríamos que utilizar una subclase de PasswordEncoder
         * en lugar de construirla sobre la marcha, pero requeriría colocar una
         * columna más en base de datos y tenerlo en cuenta en la gestión de los
         * usuarios.
         * 
         * Sería más seguro, eso sí.
         */
        final ShaPasswordEncoder encoder = new ShaPasswordEncoder();
        encoder.setEncodeHashAsBase64(true);
        return new PasswordEncoder() {

            @Override
            public String encode(CharSequence rawPassword) {
                return encoder.encodePassword(rawPassword.toString(), null);
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encodedPassword.equals(encode(rawPassword));
            }
        };
    }
}
