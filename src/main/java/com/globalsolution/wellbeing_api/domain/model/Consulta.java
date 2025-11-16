package com.globalsolution.wellbeing_api.domain.model;

import com.globalsolution.wellbeing_api.domain.model.enums.StatusConsulta;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Representa uma consulta realizada por um paciente com um profissional de saúde.
 * Contém informações de data/hora, status e observações.
 */
@Entity
@Table(name = "consultas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Consulta {

    /** Identificador único da consulta */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Paciente que participa da consulta */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    @NotNull(message = "O paciente é obrigatório")
    private Paciente paciente;

    /** Profissional de saúde responsável pela consulta */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id", nullable = false)
    @NotNull(message = "O profissional é obrigatório")
    private ProfissionalSaude profissional;

    /** Data e hora agendada da consulta */
    @NotNull(message = "A data e hora da consulta são obrigatórias")
    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    /** Status da consulta: AGENDADA, REALIZADA, CANCELADA */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusConsulta status = StatusConsulta.AGENDADA;

    /** Observações adicionais */
    @Size(max = 500, message = "A observação deve ter no máximo 500 caracteres")
    @Column(length = 500)
    private String observacao;
}
