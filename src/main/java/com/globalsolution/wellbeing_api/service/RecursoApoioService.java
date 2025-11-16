package com.globalsolution.wellbeing_api.service;

import com.globalsolution.wellbeing_api.domain.model.ProfissionalSaude;
import com.globalsolution.wellbeing_api.domain.model.RecursoApoio;
import com.globalsolution.wellbeing_api.domain.model.enums.TipoRecurso;
import com.globalsolution.wellbeing_api.repository.RecursoApoioRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Serviço responsável pela lógica de negócio relacionada aos
 * recursos de apoio emocional oferecidos pela plataforma.
 */
@Service
@RequiredArgsConstructor
public class RecursoApoioService {

    private final RecursoApoioRepository recursoApoioRepository;
    private final ProfissionalSaudeService profissionalService;
    private final CadastroBasicoService cadastroBasicoService;

    /**
     * Lista todos os recursos de apoio cadastrados.
     */
    public List<RecursoApoio> listar() {
        return cadastroBasicoService.listarTodos(recursoApoioRepository);
    }

    /**
     * Lista recursos filtrando por tipo (artigo, vídeo, livro etc.).
     */
    public List<RecursoApoio> listarPorTipo(TipoRecurso tipo) {
        return recursoApoioRepository.findByTipo(tipo);
    }

    /**
     * Busca um recurso por ID (ou falha).
     */
    public RecursoApoio buscarOuFalhar(Long id) {
        return recursoApoioRepository.findById(id)
                .orElseThrow(() -> new com.globalsolution.wellbeing_api.domain.exception.EntidadeNaoEncontradaException(
                        "Recurso de apoio não encontrado com ID: " + id
                ));
    }

    /**
     * Cadastra um novo recurso de apoio.
     *
     * Se o recurso vier associado a um profissional,
     * o ID será validado.
     */
    @Transactional
    public RecursoApoio cadastrar(RecursoApoio recurso, Long profissionalId) {

        if (profissionalId != null) {
            ProfissionalSaude profissional = profissionalService.buscarOuFalhar(profissionalId);
            recurso.setProfissional(profissional);
        }

        return cadastroBasicoService.salvar(recursoApoioRepository, recurso);
    }

    /**
     * Atualiza um recurso existente.
     */
    @Transactional
    public RecursoApoio atualizar(Long id, RecursoApoio dados, Long profissionalId) {

        RecursoApoio atual = buscarOuFalhar(id);

        atual.setTitulo(dados.getTitulo());
        atual.setDescricao(dados.getDescricao());
        atual.setTipo(dados.getTipo());
        atual.setUrl(dados.getUrl());

        // atualizar vínculo com profissional (opcional)
        if (profissionalId != null) {
            ProfissionalSaude profissional = profissionalService.buscarOuFalhar(profissionalId);
            atual.setProfissional(profissional);
        }

        return cadastroBasicoService.salvar(recursoApoioRepository, atual);
    }

    /**
     * Remove um recurso da plataforma.
     */
    @Transactional
    public void remover(Long id) {
        cadastroBasicoService.remover(recursoApoioRepository, id);
    }
}
