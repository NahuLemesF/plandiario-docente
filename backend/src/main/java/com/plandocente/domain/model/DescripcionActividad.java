package com.plandocente.domain.model;

import com.plandocente.domain.exception.ActividadInvalidaException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Value Object que encapsula la descripción de una actividad.
 * Garantiza que no sea nula, no esté vacía y no supere los 500 caracteres.
 */
@Getter
@EqualsAndHashCode
public final class DescripcionActividad {

    private static final int MAX_LONGITUD = 500;

    private final String valor;

    public DescripcionActividad(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new ActividadInvalidaException(
                    "La descripción de la actividad no puede ser nula ni vacía");
        }
        if (valor.length() > MAX_LONGITUD) {
            throw new ActividadInvalidaException(
                    "La descripción de la actividad no puede superar los " + MAX_LONGITUD + " caracteres");
        }
        this.valor = valor;
    }

    @Override
    public String toString() {
        return valor;
    }
}
