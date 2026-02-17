package com.plandocente.domain.model;

import lombok.Getter;

/**
 * Unidades Curriculares oficiales del Plan EBI - Tramo 1.
 * Cada unidad pertenece a un {@link EspacioCurricular} específico.
 *
 * <p>Los nombres Java están normalizados (sin tildes ni espacios),
 * mientras que {@code displayName} conserva el nombre oficial exacto.
 */
@Getter
public enum UnidadCurricular {

    // Científico-Matemático
    MATEMATICA("Matemática", EspacioCurricular.CIENTIFICO_MATEMATICO),
    FISICA_QUIMICA("Física Química", EspacioCurricular.CIENTIFICO_MATEMATICO),
    CIENCIAS_DEL_AMBIENTE("Ciencias del Ambiente (Biología)", EspacioCurricular.CIENTIFICO_MATEMATICO),
    CIENCIAS_DE_LA_TIERRA_Y_EL_ESPACIO("Ciencias de la Tierra y el Espacio (Geología y Astronomía)",
            EspacioCurricular.CIENTIFICO_MATEMATICO),

    // Comunicación
    LENGUA_ESPANOLA("Lengua Española", EspacioCurricular.COMUNICACION),
    SEGUNDAS_LENGUAS_Y_LENGUAS_EXTRANJERAS("Segundas Lenguas y Lenguas Extranjeras",
            EspacioCurricular.COMUNICACION),

    // Ciencias Sociales y Humanidades
    HISTORIA("Historia", EspacioCurricular.CIENCIAS_SOCIALES_Y_HUMANIDADES),
    FORMACION_PARA_LA_CIUDADANIA("Formación para la Ciudadanía",
            EspacioCurricular.CIENCIAS_SOCIALES_Y_HUMANIDADES),
    GEOGRAFIA("Geografía", EspacioCurricular.CIENCIAS_SOCIALES_Y_HUMANIDADES),

    // Creativo-Artístico
    ARTES_VISUALES_Y_PLASTICAS("Artes Visuales y Plásticas", EspacioCurricular.CREATIVO_ARTISTICO),
    MUSICA("Música", EspacioCurricular.CREATIVO_ARTISTICO),
    LITERATURA("Literatura", EspacioCurricular.CREATIVO_ARTISTICO),
    TEATRO("Teatro", EspacioCurricular.CREATIVO_ARTISTICO),
    DANZA("Danza", EspacioCurricular.CREATIVO_ARTISTICO),
    CONCIENCIA_Y_CONOCIMIENTO_CORPORAL("Conciencia y Conocimiento Corporal",
            EspacioCurricular.CREATIVO_ARTISTICO),

    // Desarrollo Personal y Conciencia Corporal
    EDUCACION_FISICA("Educación Física", EspacioCurricular.DESARROLLO_PERSONAL_Y_CONCIENCIA_CORPORAL),

    // Técnico-Tecnológico
    CIENCIAS_DE_LA_COMPUTACION_Y_TECNOLOGIA_EDUCATIVA("Ciencias de la Computación y Tecnología Educativa",
            EspacioCurricular.TECNICO_TECNOLOGICO);

    private final String displayName;
    private final EspacioCurricular espacio;

    UnidadCurricular(String displayName, EspacioCurricular espacio) {
        this.displayName = displayName;
        this.espacio = espacio;
    }
}
