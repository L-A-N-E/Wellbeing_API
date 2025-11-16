package com.globalsolution.wellbeing_api.domain.dto.condicao;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.globalsolution.wellbeing_api.domain.model.enums.Severidade;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * DTO de saída utilizado para retornar ao cliente informações
 * completas sobre uma condição emocional de um paciente.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class CondicaoPacienteResponseDTO {

    private Long id;
    private String nome;
    private String descricao;
    private Severidade severidade;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataDiagnostico;

    // Getters/Setters gerados por Lombok
}
