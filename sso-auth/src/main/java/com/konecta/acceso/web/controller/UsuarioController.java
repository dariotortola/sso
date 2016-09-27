package com.konecta.acceso.web.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.konecta.acceso.web.controller.model.CreateFindResult;
import com.konecta.sso.model.Usuario;
import com.konecta.sso.repository.UsuarioRepository;

@RestController
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @RequestMapping(value = "{query}", method = RequestMethod.GET)
    public List<Usuario> getUsuarios(@PathVariable String query) {
        return repository.findByUsernameOrMeta4OrEmail(query, query, query);
    }

    @RequestMapping(value = "username", method = RequestMethod.POST)
    public CreateFindResult<Usuario> create(@PathVariable String username) {
        CreateFindResult<Usuario> cfr = new CreateFindResult<>();
        List<Usuario> usuarios = repository.findByUsername(username);
        cfr.setCreada(usuarios.isEmpty());
        if (cfr.isCreada()) {
            Usuario usuario = new Usuario();
            usuario.setUsername(username);
            usuario = repository.save(usuario);
            cfr.setObject(usuario);
        } else {
            cfr.setObject(usuarios.get(0));
        }
        return cfr;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @Transactional
    public void update(@PathVariable Long id, @RequestBody Usuario modificado) {
        Usuario usuario = repository.findOne(id);
        usuario.setActivo(modificado.isActivo());
        usuario.setEmail(modificado.getEmail());
        usuario.setMeta4(modificado.getMeta4());
        usuario.setNombre(modificado.getNombre());
        if (!modificado.getUsername().equals(usuario.getUsername())
                && repository.findByUsername(modificado.getUsername()).isEmpty()) {
            usuario.setUsername(modificado.getUsername());
        }
        repository.save(usuario);
    }
}
