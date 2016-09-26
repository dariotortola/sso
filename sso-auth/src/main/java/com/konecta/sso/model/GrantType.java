package com.konecta.sso.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Cuando una aplicación C se conecta con el servidor de autenticación lo hace
 * con un tipo de grant. El protocolo OAuth2 define cuatro tipos, pero también
 * define un método de extensión, por lo que no vamos a limitarnos a una enum
 * 
 * Los tipos que define OAuth2 son
 * <ul>
 * <li>Authorization Code (authorization_code) for apps running on a web server
 * </li>
 * <li>Implicit (implicit) for browser-based or mobile apps</li>
 * <li>Password (password) for logging in with a username and password</li>
 * <li>Client credentials (client_credentials) for application access</li>
 * 
 * </ul>
 * 
 * @author dtortola
 *
 */
@Entity
@Table(name = "GRANT_TYPES")
public class GrantType {
    private static final String GRANT_GENERATOR = "grantTypeGenerator";
    @Id
    @GeneratedValue(generator = GRANT_GENERATOR)
    @SequenceGenerator(name = GRANT_GENERATOR, sequenceName = "GRANT_SEQUENCE")
    @Column(name = "ID")
    private Long id;
    private String name;

    @Column(name = "VERSION")
    private long version;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
