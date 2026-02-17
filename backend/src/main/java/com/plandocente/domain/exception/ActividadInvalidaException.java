package com.plandocente.domain.exception;

/**
 * Se lanza cuando una actividad viola invariantes del dominio.
 */
public class ActividadInvalidaException extends DomainException {

    public ActividadInvalidaException(String message) {
        super(message);
    }
}
