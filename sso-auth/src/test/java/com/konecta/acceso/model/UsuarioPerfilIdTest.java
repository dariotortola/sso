package com.konecta.acceso.model;

import org.junit.Assert;
import org.junit.Test;

public class UsuarioPerfilIdTest {

    @Test
    public void equals() {
        UsuarioPerfilId id1 = new UsuarioPerfilId();
        Assert.assertFalse(id1.equals(null));
        Assert.assertEquals(id1, id1);

        UsuarioPerfilId id2 = new UsuarioPerfilId();
        Assert.assertEquals(id1, id2);

        id1.setPerfil(1L);
        Assert.assertNotEquals(id1, id2);
        id2.setPerfil(id1.getPerfil());
        id2.setUsuario(2L);
        Assert.assertNotEquals(id1, id2);
        id1.setUsuario(id2.getUsuario());
        Assert.assertEquals(id1, id2);
    }
}
