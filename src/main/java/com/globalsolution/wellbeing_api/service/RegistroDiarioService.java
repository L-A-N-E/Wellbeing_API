package com.globalsolution.wellbeing_api.service;

import com.globalsolution.wellbeing_api.domain.model.Paciente;
import com.globalsolution.wellbeing_api.domain.model.RegistroDiario;
import com.globalsolution.wellbeing_api.repository.RegistroDiarioRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Serviço responsável por gerenciar os registros diários dos pacientes.
 *
 * Um registro diário contém anotações sobre o estado emocional,
 * humor, atividades e observações gerais do paciente.
 */
@Service
@RequiredArgsConstructor
public class RegistroDiarioService {

    private final RegistroDiarioRepository registroRepository;
    private final PacienteService pacienteService;
    private final CadastroBasicoService cadastroBasicoService;

    /**
     * Lista todos os registros diários existentes.
     *
     * @return lista completa de registros
     */
    public List<RegistroDiario> listar() {
        return cadastroBasicoService.listarTodos(registroRepository);
    }

    /**
     * Busca um registro pelo ID, ou lança exceção caso não exista.
     *
     * @param id identificador do registro
     * @return registro encontrado
     */
    public RegistroDiario buscarOuFalhar(Long id) {
        return cadastroBasicoService.buscarOuFalhar(registroRepository, id);
    }

    /**
     * Lista todos os registros pertencentes a um paciente específico.
     *
     * @param pacienteId id do paciente
     * @return lista de registros do paciente
     */
    public List<RegistroDiario> listarPorPaciente(Long pacienteId) {
        return registroRepository.findByPacienteId(pacienteId);
    }

    /**
     * Registra um novo diário para o paciente.
     *
     * @param pacienteId id do paciente
     * @param registro dados do registro diário
     * @return registro salvo
     */
    @Transactional
    public RegistroDiario cadastrar(Long pacienteId, RegistroDiario registro) {
        Paciente paciente = pacienteService.buscarOuFalhar(pacienteId);

        registro.setPaciente(paciente); // vincula o registro ao paciente

        return cadastroBasicoService.salvar(registroRepository, registro);
    }

    /**
     * Atualiza os dados de um registro já existente.
     *
     * @param id id do registro que será atualizado
     * @param novosDados dados atualizados
     * @return registro atualizado
     */
    @Transactional
    public RegistroDiario atualizar(Long id, RegistroDiario novosDados) {
        RegistroDiario atual = buscarOuFalhar(id);

        atual.setDataHora(novosDados.getDataHora());
        atual.setHumor(novosDados.getHumor());
        atual.setEmocao(novosDados.getEmocao());
        atual.setObservacao(novosDados.getObservacao());

        return cadastroBasicoService.salvar(registroRepository, atual);
    }

    /**
     * Remove um registro diário do sistema.
     *
     * @param id identificador do registro
     */
    @Transactional
    public void remover(Long id) {
        cadastroBasicoService.remover(registroRepository, id);
    }
}
