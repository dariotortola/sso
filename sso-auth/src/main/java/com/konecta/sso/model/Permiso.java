package com.konecta.sso.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

@Entity
@Table(name = "PERMISOS", uniqueConstraints = @UniqueConstraint(name = "PerfilUnicoPorAplicacion", columnNames = {
        "APLICACION", "CODIGO" }))
public class Permiso {
    private static final String PERMISO_GENERATOR = "PermisoGenerator";

    @ManyToOne(optional = false)
    @JoinColumn(name = "APLICACION", foreignKey = @ForeignKey(name = "PERMISOS_FK_APLICACION"))
    private Aplicacion aplicacion;
    private String codigo;
    private String descripcion;
    @Id
    @GeneratedValue(generator = PERMISO_GENERATOR)
    @SequenceGenerator(name = PERMISO_GENERATOR, sequenceName = "PERMISO_SEQUENCE")
    @Column(name = "ID")
    private Long id;
    @Version
    @Column(name = "VERSION")
    private long version;

    public Aplicacion getAplicacion() {
        return aplicacion;
    }

    public void setAplicacion(Aplicacion aplicacion) {
        this.aplicacion = aplicacion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
