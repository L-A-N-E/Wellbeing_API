package com.globalsolution.wellbeing_api.service;

import com.globalsolution.wellbeing_api.domain.model.Especialidade;
import com.globalsolution.wellbeing_api.domain.model.ProfissionalSaude;
import com.globalsolution.wellbeing_api.repository.EspecialidadeRepository;
import com.globalsolution.wellbeing_api.repository.ProfissionalSaudeRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Serviço responsável pela lógica de negócio relacionada aos
 * profissionais de saúde cadastrados no sistema.
 *
 * Funções:
 * - cadastrar profissional
 * - atualizar
 * - validar registro profissional
 * - vincular especialidades
 */
@Service
@RequiredArgsConstructor
public class ProfissionalSaudeService {

    private final ProfissionalSaudeRepository profissionalRepository;
    private final EspecialidadeRepository especialidadeRepository;
    private final CadastroBasicoService cadastroBasicoService;

    /**
     * Lista todos os profissionais.
     */
    public List<ProfissionalSaude> listar() {
        return cadastroBasicoService
                .listarTodos(profissionalRepository);
    }

    /**
     * Busca um profissional pelo ID, ou lança erro.
     */
        public ProfissionalSaude buscarOuFalhar(Long id) {
        return profissionalRepository.findById(id)
            .orElseThrow(() -> new com.globalsolution.wellbeing_api.domain.exception.EntidadeNaoEncontradaException(
                "Profissional de saúde não encontrado com ID: " + id
            ));
        }

    /**
     * Cadastra um novo profissional, garantindo:
     * - Registro profissional único (CRM/CRP)
     */
    @Transactional
    public ProfissionalSaude cadastrar(ProfissionalSaude profissional) {

        validarRegistroProfissionalUnico(profissional.getRegistroProfissional());

        return cadastroBasicoService.salvar(profissionalRepository, profissional);
    }

    /**
     * Atualiza os dados de um profissional, garantindo que
     * o registro profissional não seja duplicado.
     */
    @Transactional
    public ProfissionalSaude atualizar(Long id, ProfissionalSaude dados) {
        ProfissionalSaude atual = buscarOuFalhar(id);

        // se o registro mudou, validar unicidade
        if (!atual.getRegistroProfissional().equals(dados.getRegistroProfissional())) {
            validarRegistroProfissionalUnico(dados.getRegistroProfissional());
        }

        atual.setNome(dados.getNome());
        atual.setEmail(dados.getEmail());
        atual.setRegistroProfissional(dados.getRegistroProfissional());

        return cadastroBasicoService.salvar(profissionalRepository, atual);
    }

    /**
     * Remove um profissional.
     */
    @Transactional
    public void remover(Long id) {
        cadastroBasicoService.remover(profissionalRepository, id);
    }

    /**
     * Adiciona uma especialidade ao profissional.
     */
    @Transactional
    public ProfissionalSaude adicionarEspecialidade(Long profissionalId, Long especialidadeId) {

        ProfissionalSaude profissional = buscarOuFalhar(profissionalId);
        Especialidade especialidade = especialidadeRepository.findById(especialidadeId)
            .orElseThrow(() -> new com.globalsolution.wellbeing_api.domain.exception.EntidadeNaoEncontradaException(
                "Especialidade não encontrada: " + especialidadeId
            ));

        profissional.getEspecialidades().add(especialidade);

        return cadastroBasicoService.salvar(profissionalRepository, profissional);
    }

    /**
     * Verifica se já existe um profissional cadastrado com o
     * mesmo registro (CRM/CRP).
     */
    private void validarRegistroProfissionalUnico(String registro) {
        boolean existe = profissionalRepository.findByRegistroProfissional(registro).isPresent();
        if (existe) {
            throw new IllegalArgumentException(
                    "Já existe um profissional com o registro: " + registro
            );
        }
    }
}
