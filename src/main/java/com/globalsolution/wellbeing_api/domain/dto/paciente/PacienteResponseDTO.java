package com.globalsolution.wellbeing_api.domain.dto.paciente;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

/**
 * DTO utilizado para retornar ao cliente as informações
 * básicas do paciente.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class PacienteResponseDTO {

    /** Identificador do paciente */
    private Long id;

    /** Nome do paciente */
    private String nome;

    /** CPF do paciente */
    private String cpf;

    /** Email do paciente */
    private String email;

    /** Data de nascimento */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataNascimento;

    // Getters/Setters gerados por Lombok
}
