package com.globalsolution.wellbeing_api.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma especialidade de um profissional de saúde.
 * Ex.: Psicologia Clínica, Psiquiatria, Terapia Familiar.
 *
 * Relaciona-se com ProfissionalSaude através de ManyToMany.
 */
@Entity
@Table(name = "especialidades")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Especialidade {

    /** Identificador único da especialidade */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nome da especialidade (obrigatório e único) */
    @NotBlank(message = "O nome da especialidade é obrigatório")
    @Size(max = 80, message = "O nome da especialidade deve ter no máximo 80 caracteres")
    @Column(nullable = false, unique = true, length = 80)
    private String nome;

    /** Descrição opcional */
    @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
    @Column(length = 255)
    private String descricao;

    /**
     * Profissionais associados a esta especialidade.
     * Mapeamento inverso do ManyToMany definido em ProfissionalSaude.
     */
    @ManyToMany(mappedBy = "especialidades")
    private List<ProfissionalSaude> profissionais = new ArrayList<>();
}
