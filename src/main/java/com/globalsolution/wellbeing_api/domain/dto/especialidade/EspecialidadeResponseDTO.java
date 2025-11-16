package com.globalsolution.wellbeing_api.domain.dto.especialidade;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO de saída utilizado para retornar ao cliente
 * informações de uma especialidade.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class EspecialidadeResponseDTO {

    private Long id;
    private String nome;
    private String descricao;

    // Getters/Setters gerados por Lombok
}
