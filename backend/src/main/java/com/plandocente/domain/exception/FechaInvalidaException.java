package com.plandocente.domain.exception;

/**
 * Se lanza cuando una fecha no cumple las reglas de validación del dominio.
 */
public class FechaInvalidaException extends DomainException {

    public FechaInvalidaException(String message) {
        super(message);
    }
}
