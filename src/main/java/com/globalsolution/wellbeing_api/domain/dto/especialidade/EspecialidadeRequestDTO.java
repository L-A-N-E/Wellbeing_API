package com.globalsolution.wellbeing_api.domain.dto.especialidade;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO de entrada utilizado para cadastrar ou atualizar
 * uma especialidade de um profissional de saúde.
 */
@Getter
@Setter
public class EspecialidadeRequestDTO {

    /** Nome da especialidade */
    @NotBlank(message = "O nome da especialidade é obrigatório")
    @Size(max = 80, message = "O nome deve ter no máximo 80 caracteres")
    private String nome;

    /** Descrição opcional */
    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
    private String descricao;

    // Getters/Setters gerados por Lombok
}
