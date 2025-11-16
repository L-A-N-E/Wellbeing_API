package com.globalsolution.wellbeing_api.domain.dto.recurso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotNull;
import com.globalsolution.wellbeing_api.domain.model.enums.TipoRecurso;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO de entrada utilizado para cadastrar ou atualizar
 * recursos de apoio emocional/educacional.
 */
@Getter
@Setter
public class RecursoApoioRequestDTO {

    /** Título do recurso */
    @NotBlank(message = "O título é obrigatório")
    @Size(max = 120, message = "O título deve ter no máximo 120 caracteres")
    private String titulo;

    /** Tipo do recurso (ARTIGO, VIDEO, PODCAST, ...) */
    @NotNull(message = "O tipo é obrigatório")
    private TipoRecurso tipo;

    /** Descrição opcional do material */
    @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
    private String descricao;

    /** URL do recurso (caso exista) */
    @Pattern(regexp = "^(?i)(https?://).+", message = "A URL deve começar com http:// ou https://")
    @Size(max = 200, message = "A URL deve ter no máximo 200 caracteres")
    private String url;

    // Getters/Setters gerados por Lombok
}
