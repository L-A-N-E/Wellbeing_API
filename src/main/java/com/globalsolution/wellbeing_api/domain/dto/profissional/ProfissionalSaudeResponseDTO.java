package com.globalsolution.wellbeing_api.domain.dto.profissional;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.globalsolution.wellbeing_api.domain.dto.especialidade.EspecialidadeResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * DTO de saída que retorna informações completas
 * de um profissional de saúde, incluindo suas especialidades.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class ProfissionalSaudeResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private String registroProfissional;

    /** Lista de especialidades completas */
    private List<EspecialidadeResponseDTO> especialidades;

    // Getters/Setters gerados por Lombok
}
