package com.konecta.acceso.web.controller.model;

public class CreateFindResult<T> {
    private boolean creada;
    private T object;

    /**
     * @return true si la aplicación acaba de ser registrada en base de datos,
     *         false si ya existía
     */
    public boolean isCreada() {
        return creada;
    }

    public void setCreada(boolean creada) {
        this.creada = creada;
    }

    /**
     * @return información sobre el objeto creado
     */
    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

}
