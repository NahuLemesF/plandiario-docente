package com.plandocente.domain.model;

import lombok.Getter;

/**
 * Espacios Curriculares oficiales del Plan EBI - Tramo 1.
 * Cada espacio agrupa un conjunto de {@link UnidadCurricular}.
 */
@Getter
public enum EspacioCurricular {

    CIENTIFICO_MATEMATICO("Científico-Matemático"),
    COMUNICACION("Comunicación"),
    CIENCIAS_SOCIALES_Y_HUMANIDADES("Ciencias Sociales y Humanidades"),
    CREATIVO_ARTISTICO("Creativo-Artístico"),
    DESARROLLO_PERSONAL_Y_CONCIENCIA_CORPORAL("Desarrollo Personal y Conciencia Corporal"),
    TECNICO_TECNOLOGICO("Técnico-Tecnológico");

    private final String displayName;

    EspacioCurricular(String displayName) {
        this.displayName = displayName;
    }
}
