package com.konecta.sso.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "APLICACIONES")
public class Aplicacion {

    private static final String APLICACION_GENERATOR = "AplicacionGenerator";

    @Column(name = "ACCESS_TOKEN_VALIDITY")
    private Long accessTokenValidity;

    @ManyToMany
    @JoinTable(name = "ALLOWED_GRANT_TYPES", joinColumns = @JoinColumn(name = "CLIENTE", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "GRANT_CLIENTE")), inverseJoinColumns = @JoinColumn(name = "GRANT_TYPE", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "GRANT_TYPE")))
    private List<GrantType> allowedGrantTypes;

    @Column(name = "AUTHORITIES")
    private String authorities;
    @ManyToMany
    @JoinTable(name = "APLICACION_AUTHORITIES", joinColumns = @JoinColumn(name = "CLIENTE", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "APL_AUT_CLIENTE")), inverseJoinColumns = @JoinColumn(name = "AUTHORITY", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "APL_AUT_AUTHORITY")))
    private List<Permiso> authoritiesAsClient;
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

    @ManyToMany
    @JoinTable(name = "APLICACION_RECURSO", joinColumns = @JoinColumn(name = "CLIENTE", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "APL_RES_CLIENTE")), inverseJoinColumns = @JoinColumn(name = "RECURSO", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "APL_RES_RESOURCE")))
    private List<Aplicacion> resources;

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

    /**
     * Una lista vacía implica falta de limitaciones
     * 
     * @return lista de grant types con los que esta aplicación puede
     *         autenticarse
     */
    public List<GrantType> getAllowedGrantTypes() {
        return allowedGrantTypes;
    }

    public String getAuthorities() {
        return authorities;
    }

    /**
     * La lista será habitualmente vacía, pero se puede rellenar para (1)
     * permitir que la aplicación haga ciertas actuaciones sobre los recursos,
     * esté el usuario logado autorizado o no, o (2) que los permisos sobre la
     * aplicación de recursos que tiene el usuario cuando se conecta a través de
     * cliente se limiten a los que la aplicación cliente sea capaz de expresar
     * 
     * @return lista de permisos sobre otras aplicaciones que tiene esta
     *         aplicación sobre ellas
     */
    public List<Permiso> getAuthoritiesAsClient() {
        return authoritiesAsClient;
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

    /**
     * @return lista de aplicaciones con recursos a los que esta aplicación
     *         puede acceder. Si es vacía, no tiene restricciones
     */
    public List<Aplicacion> getResources() {
        return resources;
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

    public void setAllowedGrantTypes(List<GrantType> allowedGrantTypes) {
        this.allowedGrantTypes = allowedGrantTypes;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public void setAuthoritiesAsClient(List<Permiso> authoritiesAsClient) {
        this.authoritiesAsClient = authoritiesAsClient;
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

    public void setResources(List<Aplicacion> resources) {
        this.resources = resources;
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
