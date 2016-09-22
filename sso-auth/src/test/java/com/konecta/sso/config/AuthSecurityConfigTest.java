package com.konecta.sso.config;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public class AuthSecurityConfigTest {

    @Test
    public void test() throws Exception {
        AuthSecurityConfig config = new AuthSecurityConfig();
        Assert.assertNotNull(config.passwordEncoder());

        @SuppressWarnings("unchecked")
        HttpSecurity http = new HttpSecurity(Mockito.mock(ObjectPostProcessor.class),
                Mockito.mock(AuthenticationManagerBuilder.class), Collections.emptyMap());
        config.configure(http);
    }
}
