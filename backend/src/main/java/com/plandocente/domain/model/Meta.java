package com.plandocente.domain.model;

import com.plandocente.domain.exception.MetaInvalidaException;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * Aggregate Root que representa un objetivo pedagógico.
 * Tiene ciclo de vida independiente y puede asociarse a múltiples planificaciones.
 *
 * <p>Invariantes:
 * <ul>
 *     <li>La descripción es obligatoria (no nula ni vacía)</li>
 *     <li>El estado cumplida es reversible</li>
 *     <li>La fecha objetivo es opcional</li>
 * </ul>
 */
@Getter
public class Meta {

    private final UUID id;
    private final String descripcion;
    private boolean cumplida;
    private final LocalDate fechaObjetivo;

    private Meta(UUID id, String descripcion, boolean cumplida, LocalDate fechaObjetivo) {
        if (id == null) {
            throw new MetaInvalidaException("El ID de la meta no puede ser nulo");
        }
        if (descripcion == null || descripcion.isBlank()) {
            throw new MetaInvalidaException("La descripción de la meta es obligatoria");
        }
        this.id = id;
        this.descripcion = descripcion;
        this.cumplida = cumplida;
        this.fechaObjetivo = fechaObjetivo;
    }

    /**
     * Crea una nueva meta con UUID generado automáticamente.
     * Se inicia como no cumplida.
     *
     * @param descripcion   descripción del objetivo pedagógico
     * @param fechaObjetivo fecha objetivo (puede ser null)
     * @return nueva instancia de Meta
     */
    public static Meta create(String descripcion, LocalDate fechaObjetivo) {
        return new Meta(UUID.randomUUID(), descripcion, false, fechaObjetivo);
    }

    /**
     * Reconstruye una meta existente desde persistencia sin generar nuevo ID.
     *
     * @param id            identificador existente
     * @param descripcion   descripción del objetivo pedagógico
     * @param cumplida      estado actual de cumplimiento
     * @param fechaObjetivo fecha objetivo (puede ser null)
     * @return instancia rehidratada de Meta
     */
    public static Meta rehydrate(UUID id, String descripcion, boolean cumplida, LocalDate fechaObjetivo) {
        return new Meta(id, descripcion, cumplida, fechaObjetivo);
    }

    /**
     * Marca esta meta como cumplida.
     */
    public void marcarCumplida() {
        this.cumplida = true;
    }

    /**
     * Desmarca esta meta, volviendo al estado no cumplida.
     */
    public void desmarcarCumplida() {
        this.cumplida = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meta meta = (Meta) o;
        return Objects.equals(id, meta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
