package com.plandocente.domain.repository;

import com.plandocente.domain.model.Fecha;
import com.plandocente.domain.model.PlanificacionDiaria;

import java.util.Optional;
import java.util.UUID;

/**
 * Contrato de repositorio para el aggregate {@link PlanificacionDiaria}.
 * La implementación concreta se provee en la capa de infraestructura.
 */
public interface PlanificacionRepository {

    /**
     * Persiste una planificación diaria (creación o actualización).
     *
     * @param planificacion la planificación a guardar
     * @return la planificación persistida
     */
    PlanificacionDiaria save(PlanificacionDiaria planificacion);

    /**
     * Busca una planificación por su identificador.
     *
     * @param id el UUID de la planificación
     * @return la planificación encontrada, o vacío si no existe
     */
    Optional<PlanificacionDiaria> findById(UUID id);

    /**
     * Verifica si ya existe una planificación para la fecha indicada.
     *
     * @param fecha la fecha a verificar
     * @return true si ya existe una planificación para esa fecha
     */
    boolean existsByFecha(Fecha fecha);

    /**
     * Busca una planificación por su fecha.
     *
     * @param fecha la fecha de la planificación
     * @return la planificación encontrada, o vacío si no existe
     */
    Optional<PlanificacionDiaria> findByFecha(Fecha fecha);
}
