package com.globalsolution.wellbeing_api.domain.dto.registrodiario;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * DTO de saída utilizado para retornar ao cliente os dados
 * de um registro diário já cadastrado.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class RegistroDiarioResponseDTO {

    /** Identificador do registro diário */
    private Long id;

    /** Data e hora do registro */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataHora;

    /** Nota de humor */
    private Integer humor;

    /** Emoção predominante */
    private String emocao;

    /** Observação */
    private String observacao;

    // Getters/Setters gerados por Lombok
}
