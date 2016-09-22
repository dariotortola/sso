package com.konecta.sso.controller;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.konecta.sso.service.UserClientAuthoritiesService;

import org.junit.Assert;

@RunWith(SpringRunner.class)
public class UserControllerTest {

    @Mock
    private UserClientAuthoritiesService service;
    @InjectMocks
    private UserController controller = new UserController();

    @Test
    public void me() {
        OAuth2Authentication logged = Mockito.mock(OAuth2Authentication.class);
        List<String> permisos = Arrays.asList("uno", "dos");
        Mockito.when(service.getAuthorities(logged)).thenReturn(permisos);

        Authentication retorno = controller.getMe(logged);

        Mockito.verify(service).getAuthorities(logged);
        KonectaAuthentication kauth = (KonectaAuthentication) retorno;

        Assert.assertEquals(logged, ReflectionTestUtils.getField(kauth, "wrapped"));
        for (String s : permisos) {
            Assert.assertTrue(kauth.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals(s)));
        }
    }
}
