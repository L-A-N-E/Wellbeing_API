package com.globalsolution.wellbeing_api.repository;

import com.globalsolution.wellbeing_api.domain.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositório responsável por operações de CRUD em Paciente.
 */
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    /** Busca paciente por CPF, utilizado para garantir unicidade. */
    Optional<Paciente> findByCpf(String cpf);
}
