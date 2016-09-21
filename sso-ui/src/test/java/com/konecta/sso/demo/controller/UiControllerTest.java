package com.konecta.sso.demo.controller;

import java.security.Principal;

import org.mockito.Mockito;

import org.junit.Assert;
import org.junit.Test;

public class UiControllerTest {

    @Test
    public void getUser() {
        UiController controller = new UiController();
        Principal principal = Mockito.mock(Principal.class);
        Assert.assertEquals(principal, controller.getUser(principal));
    }
}
