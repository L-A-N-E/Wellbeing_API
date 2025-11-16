package com.globalsolution.wellbeing_api.service;

import com.globalsolution.wellbeing_api.domain.model.Especialidade;
import com.globalsolution.wellbeing_api.repository.EspecialidadeRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Serviço responsável pela lógica de negócio relacionada às especialidades
 * dos profissionais de saúde.
 *
 * Realiza operações de:
 * - cadastro
 * - atualização
 * - remoção
 * - validação de unicidade
 * - busca
 */
@Service
@RequiredArgsConstructor
public class EspecialidadeService {

    private final EspecialidadeRepository especialidadeRepository;
    private final CadastroBasicoService cadastroBasicoService;

    /**
     * Lista todas as especialidades cadastradas.
     */
    public List<Especialidade> listar() {
        return cadastroBasicoService.listarTodos(especialidadeRepository);
    }

    /**
     * Busca especialidade por ID (ou falha).
     */
    public Especialidade buscarOuFalhar(Long id) {
        return especialidadeRepository.findById(id)
                .orElseThrow(() -> new com.globalsolution.wellbeing_api.domain.exception.EntidadeNaoEncontradaException(
                        "Especialidade não encontrada com ID: " + id
                ));
    }

    /**
     * Cadastra uma nova especialidade.
     */
    @Transactional
    public Especialidade cadastrar(Especialidade especialidade) {

        validarNomeUnico(especialidade.getNome());

        return cadastroBasicoService.salvar(especialidadeRepository, especialidade);
    }

    /**
     * Atualiza uma especialidade existente.
     */
    @Transactional
    public Especialidade atualizar(Long id, Especialidade dados) {

        Especialidade atual = buscarOuFalhar(id);

        // Se o nome foi alterado, validar unicidade
        if (!atual.getNome().equalsIgnoreCase(dados.getNome())) {
            validarNomeUnico(dados.getNome());
        }

        atual.setNome(dados.getNome());
        atual.setDescricao(dados.getDescricao());

        return cadastroBasicoService.salvar(especialidadeRepository, atual);
    }

    /**
     * Remove uma especialidade do banco de dados.
     */
    @Transactional
    public void remover(Long id) {
        cadastroBasicoService.remover(especialidadeRepository, id);
    }

    /**
     * Verifica se já existe uma especialidade com o nome informado.
     */
    private void validarNomeUnico(String nome) {
        boolean existe = especialidadeRepository.findByNome(nome).isPresent();
        if (existe) {
            throw new IllegalArgumentException(
                "Já existe uma especialidade cadastrada com o nome: " + nome
            );
        }
    }
}
