package com.plandocente.domain.exception;

/**
 * Se lanza cuando una planificación diaria viola invariantes del dominio.
 */
public class PlanificacionInvalidaException extends DomainException {

    public PlanificacionInvalidaException(String message) {
        super(message);
    }
}
