package com.konecta.sso.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

@SpringBootApplication
@EnableOAuth2Sso
public class UiApplication {
    protected UiApplication() {
        // para que no se queje sonar
    }

    public static void main(String[] args) {
        SpringApplication.run(UiApplication.class, args);
    }
}
