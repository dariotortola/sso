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

/**
 * Cuando una aplicación C se autentica en el servidor de autenticación A tiene
 * que preguntarle por los datos del usuario logado en C (que se está logando en
 * A, claro). Eso es una llamada de recursos y por eso es por lo que el servidor
 * de autenticación también es un servidor de recursos.
 * 
 * Ahora bien, cuando una aplicación es cliente de un servidor de recursos,
 * tiene que tener un scope (un modo de conexión) con los que acceder a este
 * servidor de recursos. Este scope es el que se define en esta clase
 * 
 * 
 * 
 * @author dtortola
 *
 */
@Entity
@Table(name = "APLICACION_SCOPES")
public class AplicacionScope {
    private static final String SCOPE_GENERATOR = "aplicacionScopeGenerator";
    @ManyToOne
    @JoinColumn(name = "APLICACION", nullable = false, foreignKey = @ForeignKey(name = "APL_SCOPE_APLICACION"))
    private Aplicacion aplicacion;
    @Column(name = "AUTO_APPROVE")
    private boolean autoApprove;
    @Id
    @GeneratedValue(generator = SCOPE_GENERATOR)
    @SequenceGenerator(name = SCOPE_GENERATOR, sequenceName = "SCOPE_SEQUENCE")
    @Column(name = "ID")
    private Long id;
    @Column(name = "SCOPE")
    private String scope;
    @Column(name = "VERSION")
    private long version;

    public Aplicacion getAplicacion() {
        return aplicacion;
    }

    public Long getId() {
        return id;
    }

    public String getScope() {
        return scope;
    }

    public long getVersion() {
        return version;
    }

    /**
     * Un scope puede ser "auto approved", de forma que no se necesite
     * confirmación del usuario para que C conecte con A (o cualquier otra
     * aplicación de recursos) con este scope
     * 
     * @return true si el scope no requiere autorización
     */
    public boolean isAutoApprove() {
        return autoApprove;
    }

    public void setAplicacion(Aplicacion aplicacion) {
        this.aplicacion = aplicacion;
    }

    public void setAutoApprove(boolean autoApprove) {
        this.autoApprove = autoApprove;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
