package com.globalsolution.wellbeing_api.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serviço utilitário contendo operações genéricas que podem ser
 * reutilizadas pelos demais serviços do domínio.
 *
 * Ele simplifica operações comuns, como:
 * - listar todos
 * - buscar por ID (falha se não existir)
 * - salvar
 * - excluir
 *
 * O objetivo é evitar repetição de código na camada de serviço principal.
 */
@Service
public class CadastroBasicoService {

    /**
     * Lista todos os registros de uma entidade.
     *
     * @param repository Repositório da entidade
     * @param <T> Tipo da entidade
     * @return Lista com todos os registros encontrados
     */
    public <T> List<T> listarTodos(JpaRepository<T, Long> repository) {
        return repository.findAll();
    }

    /**
     * Busca uma entidade por ID, lançando erro caso não exista.
     *
     * @param repository Repositório da entidade
     * @param id ID procurado
     * @param <T> Tipo da entidade
     * @return Entidade encontrada
     * @throws EntityNotFoundException se não existir
     */
    public <T> T buscarOuFalhar(JpaRepository<T, Long> repository, Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Registro não encontrado com ID: " + id
                ));
    }

    /**
     * Salva qualquer entidade no banco.
     *
     * @param repository Repositório da entidade
     * @param entidade Entidade a ser salva
     * @param <T> Tipo da entidade
     * @return Entidade salva
     */
    public <T> T salvar(JpaRepository<T, Long> repository, T entidade) {
        return repository.save(entidade);
    }

    /**
     * Remove um registro pelo ID.
     *
     * @param repository Repositório da entidade
     * @param id ID a ser removido
     * @param <T> Tipo da entidade
     */
    public <T> void remover(JpaRepository<T, Long> repository, Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Não existe registro com ID: " + id);
        }
        repository.deleteById(id);
    }
}
