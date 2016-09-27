package com.konecta.acceso.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.konecta.sso.model.Usuario;

@Entity
@Table(name = "USUARIO_PERFIL")
@IdClass(UsuarioPerfilId.class)
public class UsuarioPerfil {
    @Column(name = "FECHA_DESDE")
    private Date fechaDesde;
    @Column(name = "FECHA_HASTA")
    private Date fechaHasta;
    @Id
    @ManyToOne
    @JoinColumn(name = "PERFIL", foreignKey = @ForeignKey(name = "USUARIO_PERFIL_FK_PERFIL"))
    private Perfil perfil;
    @Id
    @ManyToOne
    @JoinColumn(name = "USUARIO", foreignKey = @ForeignKey(name = "USUARIO_PERFIL_FK_USUARIO"))
    private Usuario usuario;

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
