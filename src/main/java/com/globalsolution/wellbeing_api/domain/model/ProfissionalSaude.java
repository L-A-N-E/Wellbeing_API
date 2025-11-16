package com.globalsolution.wellbeing_api.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa um profissional de saúde habilitado a atender pacientes.
 * Ex.: psicólogos, psiquiatras, terapeutas, etc.
 *
 * O profissional possui um registro único (CRM/CRP)
 * e pode ter várias especialidades.
 */
@Entity
@Table(name = "profissionais_saude")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfissionalSaude {

    /** Identificador único do profissional */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nome completo */
    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 120)
    @Column(nullable = false, length = 120)
    private String nome;

    /** Email de contato */
    @Email(message = "O email deve ser válido")
    @Size(max = 120)
    @Column(length = 120)
    private String email;

    /**
     * Registro profissional (CRM, CRP, etc.)
     */
    @NotBlank(message = "O registro profissional é obrigatório")
    @Size(max = 20, message = "O registro deve ter no máximo 20 caracteres")
    @Column(name = "registro_profissional", nullable = false, unique = true, length = 20)
    private String registroProfissional;

    /**
     * Especialidades do profissional.
     * Muitos profissionais podem ter muitas especialidades.
     */
    @ManyToMany
    @JoinTable(
        name = "profissional_especialidades",
        joinColumns = @JoinColumn(name = "profissional_id"),
        inverseJoinColumns = @JoinColumn(name = "especialidade_id")
    )
    private List<Especialidade> especialidades = new ArrayList<>();

    /**
     * Consultas realizadas pelo profissional.
     */
    @OneToMany(mappedBy = "profissional")
    private List<Consulta> consultas = new ArrayList<>();
}
