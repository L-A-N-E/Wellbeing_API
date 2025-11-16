package com.globalsolution.wellbeing_api.domain.dto.paciente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

/**
 * DTO de entrada utilizado para cadastrar ou atualizar
 * dados de um paciente.
 */
@Getter
@Setter
public class PacienteRequestDTO {

    /** Nome completo do paciente */
    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    private String nome;

    /** CPF do paciente */
    @NotBlank(message = "O CPF é obrigatório")
    @Size(max = 14, message = "O CPF deve ter no máximo 14 caracteres")
    @Pattern(regexp = "(\\d{11})|(\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2})", message = "Use 11 dígitos ou formato 000.000.000-00")
    private String cpf;

    /** Email do paciente */
    @Email(message = "O email deve ser válido")
    @Size(max = 100, message = "O email deve ter no máximo 100 caracteres")
    private String email;

    /** Data de nascimento (opcional) */
    @Past(message = "A data de nascimento deve ser no passado")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataNascimento;

    // Getters/Setters gerados por Lombok
}
