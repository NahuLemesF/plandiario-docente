package com.plandocente.domain.model;

import com.plandocente.domain.exception.FechaInvalidaException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;

/**
 * Value Object que encapsula una fecha válida del dominio.
 * Garantiza que la fecha nunca sea nula.
 */
@Getter
@EqualsAndHashCode
public final class Fecha {

    private final LocalDate valor;

    public Fecha(LocalDate valor) {
        if (valor == null) {
            throw new FechaInvalidaException("La fecha no puede ser nula");
        }
        this.valor = valor;
    }

    @Override
    public String toString() {
        return valor.toString();
    }
}
