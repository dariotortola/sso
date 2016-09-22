package com.konecta.sso.controller;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;

public class KonectaAuthenticationTest {

    @Test
    public void test() {
        Authentication original = Mockito.mock(Authentication.class);
        Collection<String> authorities = Arrays.asList("auth1", "auth2");
        KonectaAuthentication kAuth = new KonectaAuthentication(original, authorities);

        Assert.assertEquals(kAuth.getCredentials(), original.getCredentials());
        Assert.assertEquals(kAuth.getDetails(), original.getDetails());
        Assert.assertEquals(kAuth.getName(), original.getName());
        Assert.assertEquals(kAuth.getPrincipal(), original.getPrincipal());

        Assert.assertEquals(kAuth.getAuthorities().size(), authorities.size());
        for (String s : authorities) {
            Assert.assertTrue(kAuth.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals(s)));
        }

        Assert.assertEquals(original.isAuthenticated(), kAuth.isAuthenticated());
        original.setAuthenticated(!original.isAuthenticated());
        Assert.assertEquals(original.isAuthenticated(), kAuth.isAuthenticated());
        kAuth.setAuthenticated(!original.isAuthenticated());
        Assert.assertEquals(original.isAuthenticated(), kAuth.isAuthenticated());

        kAuth.toString();
    }
}
