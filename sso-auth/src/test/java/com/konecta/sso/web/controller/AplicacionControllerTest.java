package com.konecta.sso.web.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import com.konecta.acceso.repository.PerfilRepository;
import com.konecta.acceso.repository.PermisoRepository;
import com.konecta.sso.controller.AplicacionController;
import com.konecta.sso.repository.AplicacionRepository;

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

    @Test
    public void list() {
        controller.list(null);
        Mockito.verify(appRepo).findAll(Matchers.any(Sort.class));

        String query = "query";
        controller.list(query);
        Mockito.verify(appRepo).findByCodigoOrDescripcion(Matchers.eq(query), Matchers.any(Sort.class));
    }

    // @Test(expected = IllegalArgumentException.class)
    // public void createSinCodigo() {
    // Aplicacion aplicacion = new Aplicacion();
    // controller.create(aplicacion);
    // }
    //
    // @Test(expected = IllegalArgumentException.class)
    // public void createCodigoVacio() {
    // Aplicacion aplicacion = new Aplicacion();
    // aplicacion.setCodigo(" ");
    // controller.create(aplicacion);
    // }
    //
    // @Test
    // public void create() {
    // Aplicacion aplicacion = new Aplicacion();
    // aplicacion.setCodigo("codigo");
    //
    // Aplicacion resultado = new Aplicacion();
    // Mockito.when(appRepo.save(aplicacion)).thenReturn(resultado);
    // Assert.assertEquals(resultado, controller.create(aplicacion));
    // }
    //
    // @Test
    // public void createPerfil() {
    // Aplicacion aplicacion = new Aplicacion();
    // Perfil perfil = new Perfil();
    // Long id = 1L;
    // Mockito.when(appRepo.findOne(id)).thenReturn(aplicacion);
    //
    // Perfil resultado = new Perfil();
    // Mockito.when(perfilRepo.save(perfil)).thenReturn(resultado);
    // Assert.assertEquals(resultado, controller.createPerfil(id, perfil));
    //
    // BaseMatcher<Perfil> matcher = new BaseMatcher<Perfil>() {
    //
    // @Override
    // public void describeTo(Description description) {
    // }
    //
    // @Override
    // public boolean matches(Object item) {
    // Perfil thisPerfil = (Perfil) item;
    // return perfil.equals(thisPerfil) && thisPerfil.getId() == null
    // && thisPerfil.getAplicacion().equals(aplicacion);
    // }
    //
    // };
    // Mockito.verify(perfilRepo).save(Matchers.argThat(matcher));
    // }

}
