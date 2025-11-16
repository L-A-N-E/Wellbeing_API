package com.globalsolution.wellbeing_api.domain.dto.condicao;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.NotNull;
import com.globalsolution.wellbeing_api.domain.model.enums.Severidade;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * DTO de entrada para cadastrar ou atualizar uma condição
 * emocional de um paciente.
 */
@Getter
@Setter
public class CondicaoPacienteRequestDTO {

    /** Nome da condição (ex.: ansiedade, depressão...) */
    @NotBlank(message = "O nome da condição é obrigatório")
    @Size(max = 80, message = "O nome deve ter no máximo 80 caracteres")
    private String nome;

    /** Descrição opcional sobre a condição */
    @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
    private String descricao;

    /** Severidade da condição (LEVE, MODERADA, GRAVE) */
    @NotNull(message = "A severidade é obrigatória")
    private Severidade severidade;

    /** Data do diagnóstico (opcional, passado/presente) */
    @PastOrPresent(message = "A data deve ser no passado ou presente")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataDiagnostico;

    // Getters/Setters gerados por Lombok
}
