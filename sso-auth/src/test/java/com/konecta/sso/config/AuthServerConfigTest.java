package com.konecta.sso.config;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.test.context.junit4.SpringRunner;

import com.konecta.sso.config.AuthServerConfig;

@RunWith(SpringRunner.class)
public class AuthServerConfigTest {

    @Mock
    private DataSource datasource;
    @Mock
    private PasswordEncoder encoder;
    @InjectMocks
    private AuthServerConfig config = new AuthServerConfig();

    @Test
    public void test() throws Exception {
        Assert.assertNotNull(config.oauth2RequestFactory());

        AuthorizationServerEndpointsConfigurer endpoints = new AuthorizationServerEndpointsConfigurer();
        config.configure(endpoints);

        AuthorizationServerSecurityConfigurer security = new AuthorizationServerSecurityConfigurer();
        config.configure(security);

        Assert.assertNotNull(config.clientDetailsService());

        ClientDetailsServiceConfigurer clients = Mockito.mock(ClientDetailsServiceConfigurer.class);
        config.configure(clients);

        Assert.assertNotNull(config.tokenStore());
    }
}
