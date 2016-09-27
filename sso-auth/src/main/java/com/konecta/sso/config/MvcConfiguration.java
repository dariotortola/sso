package com.konecta.sso.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfiguration extends WebMvcConfigurerAdapter {

    public static final String ENCODER_BCRYPT = "encoderBCrypt";

    public static final String ENCODER_MD5 = "encoderMd5";
    public static final String ENCODER_SHA1 = "encoderSha1";
    public static final String ENCODER_SHA1B64 = "encoderSha1Base64";
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        /*
         * links to freemarker templates for login and client applications
         * authorizations
         */
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/oauth/confirm_access").setViewName("authorize");
    }

    /**
     * @return password encoder md5
     */
    @Bean(name = ENCODER_BCRYPT)
    public PasswordEncoder bcrypt() {
        return new BCryptPasswordEncoder();
    }

    /**
     * @return password encoder md5
     */
    @Bean(name = ENCODER_MD5)
    public PasswordEncoder md5() {
        /*
         * Sólo con incluir un PasswordEncoder, Spring lo asigna automáticamente
         * al UserDetailsService
         */
        final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
        encoder.setEncodeHashAsBase64(false);
        return wrap(encoder);
    }

    /**
     * @return password encoder sha1
     */
    @Bean(name = ENCODER_SHA1)
    public PasswordEncoder sha1() {
        /*
         * Sólo con incluir un PasswordEncoder, Spring lo asigna automáticamente
         * al UserDetailsService
         */
        final ShaPasswordEncoder encoder = new ShaPasswordEncoder();
        encoder.setEncodeHashAsBase64(false);
        return wrap(encoder);
    }

    /**
     * @return password encoder sha1 como base 64
     */
    @Bean(name = ENCODER_SHA1B64)
    public PasswordEncoder sha1b64() {
        /*
         * Sólo con incluir un PasswordEncoder, Spring lo asigna automáticamente
         * al UserDetailsService
         */
        final ShaPasswordEncoder encoder = new ShaPasswordEncoder();
        encoder.setEncodeHashAsBase64(true);
        return wrap(encoder);
    }

    private PasswordEncoder wrap(final MessageDigestPasswordEncoder encoder) {
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
