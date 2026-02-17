package com.plandocente.domain.repository;

import com.plandocente.domain.model.Meta;

import java.util.Optional;
import java.util.UUID;

/**
 * Contrato de repositorio para el aggregate {@link Meta}.
 * La implementación concreta se provee en la capa de infraestructura.
 */
public interface MetaRepository {

    /**
     * Persiste una meta (creación o actualización).
     *
     * @param meta la meta a guardar
     * @return la meta persistida
     */
    Meta save(Meta meta);

    /**
     * Busca una meta por su identificador.
     *
     * @param id el UUID de la meta
     * @return la meta encontrada, o vacío si no existe
     */
    Optional<Meta> findById(UUID id);
}
