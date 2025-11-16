package com.globalsolution.wellbeing_api.service;

import com.globalsolution.wellbeing_api.domain.model.CondicaoPaciente;
import com.globalsolution.wellbeing_api.domain.model.Paciente;
import com.globalsolution.wellbeing_api.repository.CondicaoPacienteRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Serviço responsável por gerenciar o histórico de condições emocionais
 * associadas a um paciente.
 */
@Service
@RequiredArgsConstructor
public class CondicaoPacienteService {

    private final CondicaoPacienteRepository condicaoRepository;
    private final PacienteService pacienteService;
    private final CadastroBasicoService cadastroBasicoService;

    /**
     * Lista todas as condições cadastradas.
     */
    public List<CondicaoPaciente> listar() {
        return cadastroBasicoService.listarTodos(condicaoRepository);
    }

    /**
     * Lista condições de um paciente específico.
     */
    public List<CondicaoPaciente> listarPorPaciente(Long pacienteId) {
        return condicaoRepository.findByPacienteId(pacienteId);
    }

    /**
     * Busca uma condição pelo ID.
     */
    public CondicaoPaciente buscarOuFalhar(Long id) {
        return condicaoRepository.findById(id)
                .orElseThrow(() -> new com.globalsolution.wellbeing_api.domain.exception.EntidadeNaoEncontradaException(
                        "Condição do paciente não encontrada com ID: " + id
                ));
    }

    /**
     * Cadastra uma nova condição emocional para o paciente.
     *
     * @param pacienteId id do paciente
     * @param condicao dados da condição
     * @return condição cadastrada
     */
    @Transactional
    public CondicaoPaciente cadastrar(Long pacienteId, CondicaoPaciente condicao) {

        Paciente paciente = pacienteService.buscarOuFalhar(pacienteId);

        validarData(condicao.getDataDiagnostico());

        condicao.setPaciente(paciente);

        return cadastroBasicoService.salvar(condicaoRepository, condicao);
    }

    /**
     * Atualiza uma condição existente.
     */
    @Transactional
    public CondicaoPaciente atualizar(Long id, CondicaoPaciente dados) {

        CondicaoPaciente atual = buscarOuFalhar(id);

        validarData(dados.getDataDiagnostico());

        atual.setNome(dados.getNome());
        atual.setDescricao(dados.getDescricao());
        atual.setSeveridade(dados.getSeveridade());
        atual.setDataDiagnostico(dados.getDataDiagnostico());

        return cadastroBasicoService.salvar(condicaoRepository, atual);
    }

    /**
     * Remove uma condição do histórico.
     */
    @Transactional
    public void remover(Long id) {
        cadastroBasicoService.remover(condicaoRepository, id);
    }

    /**
     * Valida se a data do diagnóstico não é no futuro.
     */
    private void validarData(LocalDate data) {
        if (data != null && data.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(
                "A data de diagnóstico não pode ser futura."
            );
        }
    }
}
