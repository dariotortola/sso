package com.konecta.acceso.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "APLICACIONES")
public class Aplicacion {

    private static final String APLICACION_GENERATOR = "AplicacionGenerator";

    @Column(name = "ACCESS_TOKEN_VALIDITY")
    private Long accessTokenValidity;

    @Column(name = "AUTHORITIES")
    private String authorities;

    @Column(name = "AUTHORIZED_GRANT_TYPES")
    private String authorizedGrantTypes;
    @Column(name = "AUTO_APPROVE")
    private String autoApprove;
    @Column(name = "CODIGO", nullable = false)
    private String codigo;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Id
    @GeneratedValue(generator = APLICACION_GENERATOR)
    @SequenceGenerator(name = APLICACION_GENERATOR, sequenceName = "APLICACION_SEQUENCE")
    @Column(name = "ID")
    private Long id;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "REFRESH_TOKEN_VALIDITY")
    private Long refreshTokenValidity;
    @Column(name = "RESOURCE_IDS")
    private String resourceIds;
    @Column(name = "SCOPE")
    private String scope;
    @Version
    @Column(name = "VERSION")
    private long version;
    @Column(name = "WEB_SERVER_REDIRECT_URI")
    private String webServerRedirectUri;
    public Long getAccessTokenValidity() {
        return accessTokenValidity;
    }
    public String getAuthorities() {
        return authorities;
    }
    public String getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }
    public String getAutoApprove() {
        return autoApprove;
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
    public String getPassword() {
        return password;
    }
    public Long getRefreshTokenValidity() {
        return refreshTokenValidity;
    }
    public String getResourceIds() {
        return resourceIds;
    }
    public String getScope() {
        return scope;
    }
    public long getVersion() {
        return version;
    }
    public String getWebServerRedirectUri() {
        return webServerRedirectUri;
    }
    public void setAccessTokenValidity(Long accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
    }
    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }
    public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }
    public void setAutoApprove(String autoApprove) {
        this.autoApprove = autoApprove;
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
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRefreshTokenValidity(Long refreshTokenValidity) {
        this.refreshTokenValidity = refreshTokenValidity;
    }
    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }
    public void setScope(String scope) {
        this.scope = scope;
    }
    public void setVersion(long version) {
        this.version = version;
    }
    public void setWebServerRedirectUri(String webServerRedirectUri) {
        this.webServerRedirectUri = webServerRedirectUri;
    }
}
