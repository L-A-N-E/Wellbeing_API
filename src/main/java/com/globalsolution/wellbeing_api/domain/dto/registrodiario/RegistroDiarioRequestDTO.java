package com.globalsolution.wellbeing_api.domain.dto.registrodiario;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * DTO de entrada para criação ou atualização
 * de registros diários do paciente.
 */
@Getter
@Setter
public class RegistroDiarioRequestDTO {

    /** Data e hora do registro */
    @NotNull(message = "A data e hora são obrigatórias")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataHora;

    /** Nota de humor (1 a 10) */
    @NotNull(message = "A nota de humor é obrigatória")
    @Min(value = 1, message = "O humor deve ser no mínimo 1")
    @Max(value = 10, message = "O humor deve ser no máximo 10")
    private Integer humor;

    /** Emoção predominante (opcional) */
    @Size(max = 255, message = "A emoção deve ter no máximo 255 caracteres")
    private String emocao;

    /** Observação opcional */
    @Size(max = 500, message = "A observação deve ter no máximo 500 caracteres")
    private String observacao;

    // Getters/Setters gerados por Lombok
}
