package com.konecta.sso.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.konecta.sso.config.MvcConfiguration;
import com.konecta.sso.controller.model.NewUsuario;
import com.konecta.sso.model.Usuario;
import com.konecta.sso.repository.UsuarioRepository;

/**
 * Funciones correspondientes a gestión
 * 
 * @author dtortola
 *
 */
@Service
public class UsuarioService {

    @Autowired
    @Qualifier(MvcConfiguration.ENCODER_BCRYPT)
    private PasswordEncoder bcrypt;

    @Autowired
    @Qualifier(MvcConfiguration.ENCODER_MD5)
    private PasswordEncoder md5;
    @Autowired
    private UsuarioRepository repository;
    @Autowired
    @Qualifier(MvcConfiguration.ENCODER_SHA1)
    private PasswordEncoder sha1;
    @Autowired
    @Qualifier(MvcConfiguration.ENCODER_SHA1B64)
    private PasswordEncoder sha1b64;

    /**
     * @param usuario
     * @return usuario creado
     */
    @Transactional
    public Usuario newUsuario(NewUsuario usuario) {
        Usuario nuevo = new Usuario();
        nuevo.setActivo(true);
        nuevo.setEmail(usuario.getEmail());
        nuevo.setMd5(md5.encode(usuario.getPassword()));
        nuevo.setMeta4(usuario.getMeta4());
        nuevo.setNombre(usuario.getNombre());
        nuevo.setSha1(sha1.encode(usuario.getPassword()));
        nuevo.setSha1base64(sha1b64.encode(usuario.getPassword()));
        nuevo.setBcrypt(bcrypt.encode(usuario.getPassword()));
        nuevo.setUsername(usuario.getUsername());
        return repository.save(nuevo);
    }
}
