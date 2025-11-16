package com.globalsolution.wellbeing_api.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Entidade que representa o registro diário do paciente,
 * incluindo humor, emoções, observações e data/hora.
 */
@Entity
@Table(name = "registros_diarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistroDiario {

    /** Identificador único do registro */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nota de humor (1 a 10) */
    @NotNull(message = "A nota de humor é obrigatória")
    @Min(value = 1, message = "O humor deve ser no mínimo 1")
    @Max(value = 10, message = "O humor deve ser no máximo 10")
    @Column(nullable = false)
    private Integer humor;

    /** Emoções predominantes */
    @Size(max = 255)
    @Column(length = 255)
    private String emocao;

    /** Observações adicionais do registro */
    @Size(max = 500)
    @Column(length = 500)
    private String observacao;

    /** Data e hora do registro */
    @NotNull(message = "A data e hora são obrigatórias")
    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    /** Paciente dono do registro */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;
}
