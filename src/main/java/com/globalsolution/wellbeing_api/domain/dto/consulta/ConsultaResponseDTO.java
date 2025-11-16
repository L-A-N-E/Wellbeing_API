package com.globalsolution.wellbeing_api.domain.dto.consulta;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.globalsolution.wellbeing_api.domain.dto.paciente.PacienteResponseDTO;
import com.globalsolution.wellbeing_api.domain.dto.profissional.ProfissionalSaudeResponseDTO;
import com.globalsolution.wellbeing_api.domain.model.enums.StatusConsulta;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * DTO de saída utilizado para retornar ao cliente as informações
 * completas de uma consulta.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class ConsultaResponseDTO {

    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataHora;
    private StatusConsulta status;
    private String observacao;

    /** Paciente vinculado à consulta */
    private PacienteResponseDTO paciente;

    /** Profissional responsável */
    private ProfissionalSaudeResponseDTO profissional;

    // Getters/Setters gerados por Lombok
}
