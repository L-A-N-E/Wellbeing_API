package com.globalsolution.wellbeing_api.domain.dto.profissional;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * DTO de entrada destinado ao cadastro ou atualização
 * de um profissional de saúde.
 */
@Getter
@Setter
public class ProfissionalSaudeRequestDTO {

    /** Nome completo do profissional */
    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 120, message = "O nome deve ter no máximo 120 caracteres")
    private String nome;

    /** Email do profissional */
    @Email(message = "O email deve ser válido")
    @Size(max = 120, message = "O email deve ter no máximo 120 caracteres")
    private String email;

    /** Registro profissional (CRM, CRP etc.) */
    @NotBlank(message = "O registro profissional é obrigatório")
    @Size(max = 20, message = "O registro deve ter no máximo 20 caracteres")
    private String registroProfissional;

    /**
     * IDs das especialidades que o profissional possui.
     * Essas especialidades serão vinculadas no service.
     */
    @NotNull(message = "A lista de especialidades é obrigatória, mesmo se vazia")
    private List<@Positive(message = "Cada ID de especialidade deve ser positivo") Long> especialidadesIds;

    // Getters/Setters gerados por Lombok
}
