package com.globalsolution.wellbeing_api.domain.model;

import com.globalsolution.wellbeing_api.domain.model.enums.Severidade;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Representa uma condição emocional ou psicológica associada a um paciente,
 * podendo incluir depressão, ansiedade, transtorno bipolar, etc.
 *
 * Serve como histórico clínico básico do paciente.
 */
@Entity
@Table(name = "condicoes_paciente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CondicaoPaciente {

    /** Identificador da condição cadastrada */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nome da condição (ex.: Depressão, Ansiedade, Burnout) */
    @NotBlank(message = "O nome da condição é obrigatório")
    @Size(max = 80, message = "O nome deve ter no máximo 80 caracteres")
    @Column(nullable = false, length = 80)
    private String nome;

    /** Detalhes adicionais sobre a condição */
    @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
    @Column(length = 500)
    private String descricao;

    /** Data do diagnóstico ou início dos sintomas */
    @PastOrPresent(message = "A data deve ser no passado ou no presente")
    @Column(name = "data_diagnostico")
    private LocalDate dataDiagnostico;

    /** Severidade da condição: LEVE, MODERADA ou GRAVE */
    @Enumerated(EnumType.STRING)
    @NotNull(message = "A severidade é obrigatória")
    @Column(nullable = false)
    private Severidade severidade;

    /** Paciente ao qual a condição pertence */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;
}
