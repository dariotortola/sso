package com.konecta.acceso.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import com.konecta.sso.model.Aplicacion;
import com.konecta.sso.model.Permiso;

@Entity
@Table(name = "PERFILES", uniqueConstraints = @UniqueConstraint(name = "PerfilUnicoPorAplicacion", columnNames = {
        "APLICACION", "CODIGO" }))
public class Perfil {
    private static final String PERFIL_GENERATOR = "PerfilGenerator";

    @ManyToOne(optional = false)
    @JoinColumn(name = "APLICACION", foreignKey = @ForeignKey(name = "PERFILES_FK_APLICACION"))
    private Aplicacion aplicacion;
    @Column(name = "CODIGO")
    private String codigo;
    private String descripcion;
    @Id
    @GeneratedValue(generator = PERFIL_GENERATOR)
    @SequenceGenerator(name = PERFIL_GENERATOR, sequenceName = "PERFIL_SEQUENCE")
    @Column(name = "ID")
    private Long id;
    @ManyToMany
    @JoinTable(name = "PERFIL_PERMISO", joinColumns = @JoinColumn(name = "PERFIL", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "PERFIL_PERMISO_FK_PERFIL")), inverseJoinColumns = @JoinColumn(name = "PERMISO", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "PERFIL_PERMISO_FK_PERMISO")))
    private List<Permiso> permisos;
    @Version
    @Column(name = "VERSION")
    private long version;

    public Aplicacion getAplicacion() {
        return aplicacion;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Long getId() {
        return id;
    }

    public List<Permiso> getPermisos() {
        return permisos;
    }

    public long getVersion() {
        return version;
    }

    public void setAplicacion(Aplicacion aplicacion) {
        this.aplicacion = aplicacion;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPermisos(List<Permiso> permisos) {
        this.permisos = permisos;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
