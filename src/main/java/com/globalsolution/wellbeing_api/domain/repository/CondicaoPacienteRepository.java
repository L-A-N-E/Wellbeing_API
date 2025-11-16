package com.globalsolution.wellbeing_api.domain.repository;

import com.globalsolution.wellbeing_api.domain.model.CondicaoPaciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositório do histórico emocional do paciente.
 */
public interface CondicaoPacienteRepository extends JpaRepository<CondicaoPaciente, Long> {

    /** Lista condições específicas de um paciente */
    List<CondicaoPaciente> findByPacienteId(Long pacienteId);
}
