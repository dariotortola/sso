package com.konecta.sso.controller.model;

/**
 * Entrada del formulario de creación de nuevo usuario
 * 
 * @author dtortola
 *
 */
public class NewUsuario {
    private String nombre;
    private String email;
    private String meta4;
    private String password;
    private String username;
    public String getEmail() {
        return email;
    }
    public String getMeta4() {
        return meta4;
    }
    public String getPassword() {
        return password;
    }
    public String getUsername() {
        return username;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setMeta4(String meta4) {
        this.meta4 = meta4;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
