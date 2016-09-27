package com.konecta.sso.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.konecta.sso.controller.model.NewUsuario;
import com.konecta.sso.controller.model.PasswordChange;
import com.konecta.sso.model.Usuario;
import com.konecta.sso.repository.UsuarioRepository;
import com.konecta.sso.service.UserClientAuthoritiesService;

@RunWith(SpringRunner.class)
public class LoggedInControllerTest {

    @Mock
    private UserClientAuthoritiesService service;
    @InjectMocks
    private LoggedInController controller = new LoggedInController();
    @Mock
    private JdbcUserDetailsManager manager;
    @Mock
    private UsuarioRepository repository;

    @Test
    public void meOAuth() {
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

    @Test
    public void meApp() {
        Authentication logged = Mockito.mock(Authentication.class);
        Assert.assertEquals(logged, controller.getMe(logged));
    }

    @Test
    public void count() {
        Principal principal = Mockito.mock(Principal.class);
        String username = "username";
        Mockito.when(principal.getName()).thenReturn(username);
        Long id = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(id);
        Mockito.when(repository.findByUsername(username)).thenReturn(Collections.singletonList(usuario));

        String param = "param";
        controller.countMeta4(param, principal);
        Mockito.verify(repository).countByMeta4IgnoreCaseAndIdNot(param, id);
        controller.countEmail(param, principal);
        Mockito.verify(repository).countByEmailIgnoreCaseAndIdNot(param, id);
        controller.countUsername(param, principal);
        Mockito.verify(repository).countByUsernameIgnoreCaseAndIdNot(param, id);
    }

    @Test
    public void changePassword() {
        PasswordChange change = new PasswordChange();
        change.setCurrent("current");
        change.setNueva("nueva");
        controller.changePassword(change);
        Mockito.verify(manager).changePassword(change.getCurrent(), change.getNueva());
    }

    @Test
    public void changeMe() {
        Principal principal = Mockito.mock(Principal.class);
        String username = "username";
        Mockito.when(principal.getName()).thenReturn(username);
        Long id = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(id);
        Mockito.when(repository.findByUsername(username)).thenReturn(Collections.singletonList(usuario));

        NewUsuario cambiado = new NewUsuario();
        cambiado.setEmail("email");
        cambiado.setMeta4("meta4");
        cambiado.setNombre("nombre");
        cambiado.setPassword("password");
        cambiado.setUsername("otroUsername");
        controller.changeMe(cambiado, principal);

        BaseMatcher<Usuario> matcher = new BaseMatcher<Usuario>() {

            @Override
            public void describeTo(Description description) {
            }

            @Override
            public boolean matches(Object item) {
                Usuario db = (Usuario) item;
                Assert.assertEquals(cambiado.getEmail(), db.getEmail());
                Assert.assertEquals(cambiado.getMeta4(), db.getMeta4());
                Assert.assertEquals(cambiado.getNombre(), db.getNombre());
                Assert.assertEquals(cambiado.getUsername(), db.getUsername());
                // la password no tiene que haber cambiado
                Assert.assertNull(db.getBcrypt());
                Assert.assertNull(db.getMd5());
                Assert.assertNull(db.getSha1());
                Assert.assertNull(db.getSha1base64());
                return true;
            }

        };

        Mockito.verify(repository).save(Matchers.argThat(matcher));
    }
}
