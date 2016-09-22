package com.konecta.sso.config;

import java.util.Collections;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

public class ResourceServerConfigTest {

    @Test
    public void test() throws Exception {
        ResourceServerConfig config = new ResourceServerConfig();

        @SuppressWarnings("unchecked")
        HttpSecurity http = new HttpSecurity(Mockito.mock(ObjectPostProcessor.class),
                Mockito.mock(AuthenticationManagerBuilder.class), Collections.emptyMap());
        config.configure(http);

        ResourceServerSecurityConfigurer resources = new ResourceServerSecurityConfigurer();
        config.configure(resources);
    }
}
