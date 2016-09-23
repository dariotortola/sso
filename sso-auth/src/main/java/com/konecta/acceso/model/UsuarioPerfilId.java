package com.konecta.acceso.model;

import java.io.Serializable;
import java.util.Objects;

public class UsuarioPerfilId implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -4821108454208643698L;

    private Long perfil;
    private Long usuario;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UsuarioPerfilId) {
            UsuarioPerfilId other = (UsuarioPerfilId) obj;
            return Objects.equals(perfil, other.perfil) && Objects.equals(usuario, other.usuario);
        }
        return false;
    }
    public Long getPerfil() {
        return perfil;
    }

    public Long getUsuario() {
        return usuario;
    }

    @Override
    public int hashCode() {
        return Objects.hash(perfil, usuario);
    }

    public void setPerfil(Long perfil) {
        this.perfil = perfil;
    }

    public void setUsuario(Long usuario) {
        this.usuario = usuario;
    }
}
