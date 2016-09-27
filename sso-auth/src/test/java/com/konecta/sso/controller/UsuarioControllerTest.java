package com.konecta.sso.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import com.konecta.sso.controller.model.NewUsuario;
import com.konecta.sso.repository.UsuarioRepository;
import com.konecta.sso.service.UsuarioService;

@RunWith(SpringRunner.class)
public class UsuarioControllerTest {

    @Mock
    private UsuarioRepository repository;
    @Mock
    private UsuarioService service;
    @InjectMocks
    private UsuarioController controller = new UsuarioController();

    @Test
    public void newUsuario() {
        NewUsuario param = new NewUsuario();
        controller.newUsuario(param);
        Mockito.verify(service).newUsuario(param);
    }

    @Test
    public void count() {
        String param = "param";
        controller.countMeta4(param);
        Mockito.verify(repository).countByMeta4IgnoreCase(param);
        controller.countEmail(param);
        Mockito.verify(repository).countByEmailIgnoreCase(param);
        controller.countUsername(param);
        Mockito.verify(repository).countByUsernameIgnoreCase(param);
    }
}
