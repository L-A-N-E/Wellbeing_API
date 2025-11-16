package com.globalsolution.wellbeing_api.domain.repository;

import com.globalsolution.wellbeing_api.domain.model.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositório das consultas.
 */
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    /** Lista consultas de um paciente */
    List<Consulta> findByPacienteId(Long pacienteId);

    /** Lista consultas de um profissional */
    List<Consulta> findByProfissionalId(Long profissionalId);
}
