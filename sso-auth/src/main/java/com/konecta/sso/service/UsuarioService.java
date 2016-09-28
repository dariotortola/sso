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
     * Cambia las password del usuario con este username, sin comprobar la
     * anterior
     * 
     * @param username
     *            debe existir un usuario con este username
     * @param newPassword
     */
    @Transactional
    public void changePassword(String username, String newPassword) {
        Usuario usuario = repository.findByUsername(username).get(0);
        changePasswords(newPassword, usuario);
        repository.save(usuario);
    }

    /**
     * Codifica todas las passwords de usuario
     * 
     * @param newPassword
     * @param usuario
     */
    private void changePasswords(String newPassword, Usuario usuario) {
        usuario.setMd5(md5.encode(newPassword));
        usuario.setSha1(sha1.encode(newPassword));
        usuario.setSha1base64(sha1b64.encode(newPassword));
        usuario.setBcrypt(bcrypt.encode(newPassword));
    }

    /**
     * @param usuario
     * @return usuario creado
     */
    @Transactional
    public Usuario newUsuario(NewUsuario usuario) {
        Usuario nuevo = new Usuario();
        nuevo.setActivo(true);
        nuevo.setEmail(usuario.getEmail());
        changePasswords(usuario.getPassword(), nuevo);
        nuevo.setMeta4(usuario.getMeta4());
        nuevo.setNombre(usuario.getNombre());
        nuevo.setUsername(usuario.getUsername());
        return repository.save(nuevo);
    }
}
