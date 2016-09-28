package com.konecta.sso.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.konecta.sso.controller.model.NewUsuario;
import com.konecta.sso.model.Usuario;
import com.konecta.sso.repository.UsuarioRepository;

import java.util.Collections;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Assert;

@RunWith(SpringRunner.class)
public class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService service = new UsuarioService();

    @Mock
    private PasswordEncoder bcrypt;
    @Mock
    private PasswordEncoder sha1;
    @Mock
    private PasswordEncoder sha1b64;
    @Mock
    private PasswordEncoder md5;
    @Mock
    private UsuarioRepository repository;

    @Test
    public void newUsuario() {
        NewUsuario nu = new NewUsuario();
        nu.setEmail("email");
        nu.setMeta4("meta4");
        nu.setNombre("nombre");
        nu.setPassword("password");
        nu.setUsername("username");

        String vMd5 = "md5";
        Mockito.when(md5.encode(nu.getPassword())).thenReturn(vMd5);
        String vSha1 = "sha1";
        Mockito.when(sha1.encode(nu.getPassword())).thenReturn(vSha1);
        String vsha1b64 = "sha1b64";
        Mockito.when(sha1b64.encode(nu.getPassword())).thenReturn(vsha1b64);
        String vBcrypt = "bcrypt";
        Mockito.when(bcrypt.encode(nu.getPassword())).thenReturn(vBcrypt);

        BaseMatcher<Usuario> matcher = new BaseMatcher<Usuario>() {

            @Override
            public boolean matches(Object item) {
                Usuario guardado = (Usuario) item;
                Assert.assertEquals(nu.getEmail(), guardado.getEmail());
                Assert.assertEquals(nu.getNombre(), guardado.getNombre());
                Assert.assertEquals(nu.getUsername(), guardado.getUsername());
                Assert.assertEquals(nu.getMeta4(), guardado.getMeta4());
                Assert.assertEquals(vMd5, guardado.getMd5());
                Assert.assertEquals(vSha1, guardado.getSha1());
                Assert.assertEquals(vsha1b64, guardado.getSha1base64());
                Assert.assertEquals(vBcrypt, guardado.getBcrypt());
                return true;
            }

            @Override
            public void describeTo(Description description) {
            }
        };

        Usuario guardado = new Usuario();
        Mockito.when(repository.save(Matchers.argThat(matcher))).thenReturn(guardado);
        Assert.assertEquals(guardado, service.newUsuario(nu));
        Mockito.verify(repository).save(Matchers.argThat(matcher));

    }

    @Test
    public void changePassword() {
        String username = "username";
        String password = "password";

        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        Mockito.when(repository.findByUsername(username)).thenReturn(Collections.singletonList(usuario));

        String vMd5 = "md5";
        Mockito.when(md5.encode(password)).thenReturn(vMd5);
        String vSha1 = "sha1";
        Mockito.when(sha1.encode(password)).thenReturn(vSha1);
        String vsha1b64 = "sha1b64";
        Mockito.when(sha1b64.encode(password)).thenReturn(vsha1b64);
        String vBcrypt = "bcrypt";
        Mockito.when(bcrypt.encode(password)).thenReturn(vBcrypt);

        service.changePassword(username, password);
        Mockito.verify(repository).save(usuario);
        Assert.assertEquals(vMd5, usuario.getMd5());
        Assert.assertEquals(vsha1b64, usuario.getSha1base64());
        Assert.assertEquals(vSha1, usuario.getSha1());
        Assert.assertEquals(vBcrypt, usuario.getBcrypt());
    }
}
