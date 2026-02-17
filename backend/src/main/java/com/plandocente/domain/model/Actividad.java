package com.plandocente.domain.model;

import com.plandocente.domain.exception.ActividadInvalidaException;
import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

/**
 * Entidad interna que representa una tarea específica dentro de una planificación diaria.
 * Pertenece al aggregate {@link PlanificacionDiaria} y no existe fuera de él.
 *
 * <p>Invariantes:
 * <ul>
 *     <li>Descripción obligatoria</li>
 *     <li>Unidad curricular obligatoria</li>
 *     <li>Duración estimada mayor a 0 minutos</li>
 * </ul>
 */
@Getter
public class Actividad {

    private final UUID id;
    private final DescripcionActividad descripcion;
    private final UnidadCurricular unidadCurricular;
    private final int duracionEstimadaMinutos;

    private Actividad(UUID id, DescripcionActividad descripcion, UnidadCurricular unidadCurricular,
                      int duracionEstimadaMinutos) {
        if (id == null) {
            throw new ActividadInvalidaException("El ID de la actividad no puede ser nulo");
        }
        if (descripcion == null) {
            throw new ActividadInvalidaException("La descripción de la actividad es obligatoria");
        }
        if (unidadCurricular == null) {
            throw new ActividadInvalidaException("La unidad curricular es obligatoria");
        }
        if (duracionEstimadaMinutos <= 0) {
            throw new ActividadInvalidaException(
                    "La duración estimada debe ser mayor a 0 minutos");
        }
        this.id = id;
        this.descripcion = descripcion;
        this.unidadCurricular = unidadCurricular;
        this.duracionEstimadaMinutos = duracionEstimadaMinutos;
    }

    /**
     * Crea una nueva actividad con UUID generado automáticamente.
     *
     * @param descripcion          descripción de la actividad
     * @param unidadCurricular     unidad curricular asociada
     * @param duracionEstimadaMinutos duración estimada en minutos
     * @return nueva instancia de Actividad
     */
    public static Actividad create(DescripcionActividad descripcion, UnidadCurricular unidadCurricular,
                                   int duracionEstimadaMinutos) {
        return new Actividad(UUID.randomUUID(), descripcion, unidadCurricular, duracionEstimadaMinutos);
    }

    /**
     * Reconstruye una actividad existente desde persistencia sin generar nuevo ID.
     *
     * @param id                   identificador existente
     * @param descripcion          descripción de la actividad
     * @param unidadCurricular     unidad curricular asociada
     * @param duracionEstimadaMinutos duración estimada en minutos
     * @return instancia rehidratada de Actividad
     */
    public static Actividad rehydrate(UUID id, DescripcionActividad descripcion,
                                      UnidadCurricular unidadCurricular, int duracionEstimadaMinutos) {
        return new Actividad(id, descripcion, unidadCurricular, duracionEstimadaMinutos);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actividad actividad = (Actividad) o;
        return Objects.equals(id, actividad.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
