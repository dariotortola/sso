package com.konecta.acceso.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "USUARIOS")
public class Usuario {
    private static final String USUARIO_GENERATOR = "usuarioGenerator";

    @Column(name = "ACTIVO")
    private boolean activo;
    @Column(name = "EMAIL")
    private String email;
    @Id
    @GeneratedValue(generator = USUARIO_GENERATOR)
    @SequenceGenerator(name = USUARIO_GENERATOR, sequenceName = "USUARIO_SEQUENCE")
    @Column(name = "ID")
    private Long id;
    @Column(name = "MD5")
    private String md5;
    @Column(name = "META4")
    private String meta4;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "SHA1")
    private String sha1;
    @Column(name = "SHA1_BASE64")
    private String sha1base64;
    @Column(name = "USERNAME")
    private String username;
    @Version
    private Long version;

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public String getMd5() {
        return md5;
    }

    public String getMeta4() {
        return meta4;
    }

    public String getNombre() {
        return nombre;
    }

    public String getSha1() {
        return sha1;
    }

    public String getSha1base64() {
        return sha1base64;
    }

    public String getUsername() {
        return username;
    }

    public Long getVersion() {
        return version;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public void setMeta4(String meta4) {
        this.meta4 = meta4;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }

    public void setSha1base64(String sha1base64) {
        this.sha1base64 = sha1base64;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
