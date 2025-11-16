package com.globalsolution.wellbeing_api.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade que representa um paciente no sistema.
 * Utiliza Lombok para geração automática de getters, setters e construtores.
 */
@Entity
@Table(name = "pacientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Paciente {

    /** Identificador único do paciente */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nome completo do paciente */
    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nome;

    /** CPF do paciente (único) */
    @NotBlank(message = "O CPF é obrigatório")
    @Size(max = 14, message = "O CPF deve ter no máximo 14 caracteres")
    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    /** Email opcional */
    @Email(message = "O email deve ser válido")
    @Size(max = 100)
    @Column(length = 100)
    private String email;

    /** Data de nascimento */
    @Past(message = "A data de nascimento deve ser no passado")
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    /** Registros diários pertencentes ao paciente */
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RegistroDiario> registros = new ArrayList<>();

    /** Consultas realizadas pelo paciente */
    @OneToMany(mappedBy = "paciente")
    private List<Consulta> consultas = new ArrayList<>();
}
