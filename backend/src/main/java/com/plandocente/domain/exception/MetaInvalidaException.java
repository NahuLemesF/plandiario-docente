package com.plandocente.domain.exception;

/**
 * Se lanza cuando una meta viola invariantes del dominio.
 */
public class MetaInvalidaException extends DomainException {

    public MetaInvalidaException(String message) {
        super(message);
    }
}
