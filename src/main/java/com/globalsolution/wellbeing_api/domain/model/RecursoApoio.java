package com.globalsolution.wellbeing_api.domain.model;

import com.globalsolution.wellbeing_api.domain.model.enums.TipoRecurso;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

/**
 * Representa um recurso de apoio emocional,
 * que pode ser um artigo, vídeo, podcast, livro, etc.
 *
 * Esses recursos podem ser recomendados a pacientes
 * por profissionais de saúde durante uma consulta.
 */
@Entity
@Table(name = "recursos_apoio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecursoApoio {

    /** Identificador único do recurso */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Título do recurso */
    @NotBlank(message = "O título do recurso é obrigatório")
    @Size(max = 120, message = "O título deve ter no máximo 120 caracteres")
    @Column(nullable = false, length = 120)
    private String titulo;

    /** Descrição curta do recurso */
    @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
    @Column(length = 500)
    private String descricao;

    /** URL do recurso (caso exista: artigo, vídeo, podcast...) */
    @Size(max = 200)
    @Column(length = 200)
    private String url;

    /** Tipo do recurso (ARTIGO, VIDEO, PODCAST...) */
    @Enumerated(EnumType.STRING)
    @NotNull(message = "O tipo do recurso é obrigatório")
    @Column(nullable = false)
    private TipoRecurso tipo;

    /**
     * Profissional que recomendou o recurso.
     * É opcional e pode ser nulo.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id")
    private ProfissionalSaude profissional;
}
