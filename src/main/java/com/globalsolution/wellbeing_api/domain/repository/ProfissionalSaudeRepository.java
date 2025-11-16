package com.globalsolution.wellbeing_api.domain.repository;

import com.globalsolution.wellbeing_api.domain.model.ProfissionalSaude;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositório para profissionais de saúde.
 */
public interface ProfissionalSaudeRepository extends JpaRepository<ProfissionalSaude, Long> {

    /** Busca por CRM/CRP (registro profissional) */
    Optional<ProfissionalSaude> findByRegistroProfissional(String registroProfissional);
}
