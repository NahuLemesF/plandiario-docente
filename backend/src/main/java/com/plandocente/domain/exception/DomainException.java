package com.plandocente.domain.exception;

/**
 * Excepción base abstracta para todas las excepciones del dominio.
 * Las capas superiores pueden capturar este tipo para manejar errores de negocio.
 */
public abstract class DomainException extends RuntimeException {

    protected DomainException(String message) {
        super(message);
    }
}
