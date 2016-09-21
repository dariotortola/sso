package com.konecta.sso.demo.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UiController {

    @RequestMapping
    public Principal getUser(Principal principal) {
        return principal;
    }
}
