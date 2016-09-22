package com.konecta.sso.config;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;

public class MethodSecurityConfigTest {

    @Test
    public void test() {
        MethodSecurityExpressionHandler exphandler = new MethodSecurityConfig().createExpressionHandler();
        Assert.assertTrue(exphandler instanceof OAuth2MethodSecurityExpressionHandler);
    }
}
