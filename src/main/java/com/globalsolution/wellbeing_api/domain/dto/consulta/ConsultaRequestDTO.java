package com.globalsolution.wellbeing_api.domain.dto.consulta;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * DTO de entrada utilizado para criar ou atualizar
 * uma consulta médica/psicológica.
 */
@Getter
@Setter
public class ConsultaRequestDTO {

    /** Data e hora da consulta */
    @NotNull(message = "A data da consulta é obrigatória")
    @Future(message = "A data deve ser no futuro")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataHora;

    /** Observação opcional realizada pelo profissional */
    @Size(max = 500, message = "A observação deve ter no máximo 500 caracteres")
    private String observacao;

    /** ID do paciente */
    @NotNull(message = "O ID do paciente é obrigatório")
    private Long pacienteId;

    /** ID do profissional responsável */
    @NotNull(message = "O ID do profissional é obrigatório")
    private Long profissionalId;

    // Getters/Setters gerados por Lombok
}
