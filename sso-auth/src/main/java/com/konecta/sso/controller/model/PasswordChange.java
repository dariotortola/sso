package com.konecta.sso.controller.model;

public class PasswordChange {

    private String current;
    private String nueva;
    public String getCurrent() {
        return current;
    }
    public String getNueva() {
        return nueva;
    }
    public void setCurrent(String current) {
        this.current = current;
    }
    public void setNueva(String nueva) {
        this.nueva = nueva;
    }
}
