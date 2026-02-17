package com.plandocente.domain.model;

import com.plandocente.domain.exception.PlanificacionInvalidaException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Aggregate Root que representa la planificación pedagógica de un día específico.
 * Contiene actividades como entidades hijas y referencias a metas por ID.
 *
 * <p>Invariantes:
 * <ul>
 *     <li>La fecha es obligatoria</li>
 *     <li>Máximo {@value #MAX_ACTIVIDADES} actividades</li>
 *     <li>Se permiten actividades duplicadas (mismo contenido, distinto ID)</li>
 * </ul>
 */
@Getter
public class PlanificacionDiaria {

    private static final int MAX_ACTIVIDADES = 5;

    private final UUID id;
    private final Fecha fecha;
    private final List<Actividad> actividades;
    private final Set<UUID> metasIds;

    private PlanificacionDiaria(UUID id, Fecha fecha, List<Actividad> actividades, Set<UUID> metasIds) {
        if (id == null) {
            throw new PlanificacionInvalidaException(
                    "El ID de la planificación no puede ser nulo");
        }
        if (fecha == null) {
            throw new PlanificacionInvalidaException(
                    "La fecha de la planificación es obligatoria");
        }
        if (actividades == null) {
            throw new PlanificacionInvalidaException(
                    "La lista de actividades no puede ser nula");
        }
        if (actividades.size() > MAX_ACTIVIDADES) {
            throw new PlanificacionInvalidaException(
                    "La planificación no puede tener más de " + MAX_ACTIVIDADES + " actividades");
        }
        if (metasIds == null) {
            throw new PlanificacionInvalidaException(
                    "El conjunto de metas no puede ser nulo");
        }
        this.id = id;
        this.fecha = fecha;
        this.actividades = new ArrayList<>(actividades);
        this.metasIds = new HashSet<>(metasIds);
    }

    /**
     * Crea una nueva planificación diaria con UUID generado automáticamente.
     * Se inicia sin actividades ni metas asociadas.
     *
     * @param fecha fecha de la planificación
     * @return nueva instancia de PlanificacionDiaria
     */
    public static PlanificacionDiaria create(Fecha fecha) {
        return new PlanificacionDiaria(UUID.randomUUID(), fecha, new ArrayList<>(), new HashSet<>());
    }

    /**
     * Reconstruye una planificación existente desde persistencia sin generar nuevo ID.
     *
     * @param id          identificador existente
     * @param fecha       fecha de la planificación
     * @param actividades lista de actividades existentes
     * @param metasIds    conjunto de IDs de metas asociadas
     * @return instancia rehidratada de PlanificacionDiaria
     */
    public static PlanificacionDiaria rehydrate(UUID id, Fecha fecha, List<Actividad> actividades,
                                                Set<UUID> metasIds) {
        return new PlanificacionDiaria(id, fecha, actividades, metasIds);
    }

    /**
     * Agrega una actividad a la planificación.
     *
     * @param actividad la actividad a agregar
     * @throws PlanificacionInvalidaException si se supera el máximo de actividades
     */
    public void agregarActividad(Actividad actividad) {
        if (actividad == null) {
            throw new PlanificacionInvalidaException("La actividad no puede ser nula");
        }
        if (actividades.size() >= MAX_ACTIVIDADES) {
            throw new PlanificacionInvalidaException(
                    "No se pueden agregar más de " + MAX_ACTIVIDADES
                            + " actividades a la planificación");
        }
        actividades.add(actividad);
    }

    /**
     * Elimina una actividad de la planificación por su ID.
     * Se usa UUID porque se permiten actividades con contenido duplicado.
     *
     * @param actividadId el UUID de la actividad a eliminar
     * @throws PlanificacionInvalidaException si no se encuentra la actividad
     */
    public void eliminarActividad(UUID actividadId) {
        if (actividadId == null) {
            throw new PlanificacionInvalidaException(
                    "El ID de la actividad no puede ser nulo");
        }
        boolean removed = actividades.removeIf(a -> a.getId().equals(actividadId));
        if (!removed) {
            throw new PlanificacionInvalidaException(
                    "No se encontró la actividad con ID: " + actividadId);
        }
    }

    /**
     * Asocia una meta a esta planificación por su ID.
     *
     * @param metaId el UUID de la meta a asociar
     */
    public void asociarMeta(UUID metaId) {
        if (metaId == null) {
            throw new PlanificacionInvalidaException("El ID de la meta no puede ser nulo");
        }
        metasIds.add(metaId);
    }

    /**
     * Desasocia una meta de esta planificación por su ID.
     *
     * @param metaId el UUID de la meta a desasociar
     * @throws PlanificacionInvalidaException si la meta no está asociada
     */
    public void desasociarMeta(UUID metaId) {
        if (metaId == null) {
            throw new PlanificacionInvalidaException("El ID de la meta no puede ser nulo");
        }
        boolean removed = metasIds.remove(metaId);
        if (!removed) {
            throw new PlanificacionInvalidaException(
                    "La meta con ID " + metaId + " no está asociada a esta planificación");
        }
    }

    /**
     * @return copia inmutable de la lista de actividades
     */
    public List<Actividad> getActividades() {
        return Collections.unmodifiableList(actividades);
    }

    /**
     * @return copia inmutable del conjunto de IDs de metas
     */
    public Set<UUID> getMetasIds() {
        return Collections.unmodifiableSet(metasIds);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlanificacionDiaria that = (PlanificacionDiaria) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
