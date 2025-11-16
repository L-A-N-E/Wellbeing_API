package com.globalsolution.wellbeing_api.repository;

import com.globalsolution.wellbeing_api.domain.model.Especialidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositório de especialidades.
 */
public interface EspecialidadeRepository extends JpaRepository<Especialidade, Long> {

    Optional<Especialidade> findByNome(String nome);
}
