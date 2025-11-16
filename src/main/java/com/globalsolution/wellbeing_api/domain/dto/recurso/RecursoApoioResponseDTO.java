package com.globalsolution.wellbeing_api.domain.dto.recurso;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.globalsolution.wellbeing_api.domain.model.enums.TipoRecurso;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO de saída utilizado para retornar ao cliente
 * dados completos sobre um recurso de apoio.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class RecursoApoioResponseDTO {

    private Long id;
    private String titulo;
    private TipoRecurso tipo;
    private String descricao;
    private String url;

    // Getters/Setters gerados por Lombok
}
