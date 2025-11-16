package com.globalsolution.wellbeing_api.domain.repository;

import com.globalsolution.wellbeing_api.domain.model.RecursoApoio;
import com.globalsolution.wellbeing_api.domain.model.enums.TipoRecurso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositório de materiais de apoio emocional.
 */
public interface RecursoApoioRepository extends JpaRepository<RecursoApoio, Long> {

    /** Filtrar por tipo (artigo, vídeo, podcast, etc.) */
    List<RecursoApoio> findByTipo(TipoRecurso tipo);
}
