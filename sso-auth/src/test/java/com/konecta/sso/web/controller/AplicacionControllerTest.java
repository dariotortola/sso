package com.konecta.sso.web.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import com.konecta.acceso.model.Perfil;
import com.konecta.acceso.repository.AplicacionRepository;
import com.konecta.acceso.repository.PerfilRepository;
import com.konecta.acceso.repository.PermisoRepository;
import com.konecta.acceso.web.controller.AplicacionController;
import com.konecta.sso.model.Aplicacion;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Assert;

@RunWith(SpringRunner.class)
public class AplicacionControllerTest {

    @Mock
    private PerfilRepository perfilRepo;
    @Mock
    private PermisoRepository permisoRepo;
    @Mock
    private AplicacionRepository appRepo;

    @InjectMocks
    private AplicacionController controller;

    @Test(expected = IllegalArgumentException.class)
    public void createSinCodigo() {
        Aplicacion aplicacion = new Aplicacion();
        controller.create(aplicacion);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createCodigoVacio() {
        Aplicacion aplicacion = new Aplicacion();
        aplicacion.setCodigo(" ");
        controller.create(aplicacion);
    }

    @Test
    public void create() {
        Aplicacion aplicacion = new Aplicacion();
        aplicacion.setCodigo("codigo");

        Aplicacion resultado = new Aplicacion();
        Mockito.when(appRepo.save(aplicacion)).thenReturn(resultado);
        Assert.assertEquals(resultado, controller.create(aplicacion));
    }

    @Test
    public void createPerfil() {
        Aplicacion aplicacion = new Aplicacion();
        Perfil perfil = new Perfil();
        Long id = 1L;
        Mockito.when(appRepo.findOne(id)).thenReturn(aplicacion);

        Perfil resultado = new Perfil();
        Mockito.when(perfilRepo.save(perfil)).thenReturn(resultado);
        Assert.assertEquals(resultado, controller.createPerfil(id, perfil));

        BaseMatcher<Perfil> matcher = new BaseMatcher<Perfil>() {

            @Override
            public void describeTo(Description description) {
            }

            @Override
            public boolean matches(Object item) {
                Perfil thisPerfil = (Perfil) item;
                return perfil.equals(thisPerfil) && thisPerfil.getId() == null
                        && thisPerfil.getAplicacion().equals(aplicacion);
            }

        };
        Mockito.verify(perfilRepo).save(Matchers.argThat(matcher));
    }
    
    
}
