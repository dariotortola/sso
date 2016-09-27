package com.konecta.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.konecta.sso.controller.model.NewUsuario;
import com.konecta.sso.model.Usuario;
import com.konecta.sso.repository.UsuarioRepository;
import com.konecta.sso.service.UsuarioService;

/**
 * Controlador para funciones de mantenimiento de usuarios
 * 
 * @author dtortola
 *
 */
@RestController
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private UsuarioService service;

    /**
     * Para evitar problemas, el usuario no debería tener el mismo username,
     * email o meta4 que otro usuario
     * 
     * @param usuario
     *            tal que respeta las restricciones de base de datos (descritas
     *            en la clase del modelo, actualmente restringidas a que tiene
     *            que tener código y éste no ser nulo)
     * @see Usuario
     */
    @PostMapping
    public void newUsuario(@RequestBody NewUsuario usuario) {
        service.newUsuario(usuario);
    }

    @GetMapping("count/username")
    public long countUsername(@RequestParam(required=false) String username) {
        return repository.countByUsernameIgnoreCase(username);
    }

    @GetMapping("count/meta4")
    public long countMeta4(@RequestParam(required=false) String meta4) {
        return repository.countByMeta4IgnoreCase(meta4);
    }

    @GetMapping("count/email")
    public long countEmail(@RequestParam(required=false) String email) {
        return repository.countByEmailIgnoreCase(email);
    }
}
