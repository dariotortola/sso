package com.konecta.sso.controller.model;

public class SimpleResponse {

    private static final SimpleResponse SUCCESS = new SimpleResponse(true, null);

    private final String error;

    private final boolean success;

    private SimpleResponse(boolean success, String error) {
        super();
        this.success = success;
        this.error = error;
    }

    public static SimpleResponse error(String message) {
        return new SimpleResponse(false, message);
    }

    public static SimpleResponse success() {
        return SUCCESS;
    }

    public static SimpleResponse warning(String warning) {
        return new SimpleResponse(true, warning);
    }

    /**
     * @return mensaje de error o warning (dependiendo de is success)
     */
    public String getError() {
        return error;
    }

    /**
     * @return true si la operación tiene éxito (aunque sea con warnings)
     */
    public boolean isSuccess() {
        return success;
    }
}
