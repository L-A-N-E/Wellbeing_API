package com.globalsolution.wellbeing_api.domain.repository;

import com.globalsolution.wellbeing_api.domain.model.RegistroDiario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositório de registros diários do paciente.
 */
public interface RegistroDiarioRepository extends JpaRepository<RegistroDiario, Long> {

    /** Lista todos os registros de um paciente específico */
    List<RegistroDiario> findByPacienteId(Long pacienteId);
}
