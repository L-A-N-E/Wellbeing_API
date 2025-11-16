package com.globalsolution.wellbeing_api.service;

import com.globalsolution.wellbeing_api.domain.model.Consulta;
import com.globalsolution.wellbeing_api.domain.model.Paciente;
import com.globalsolution.wellbeing_api.domain.model.ProfissionalSaude;
import com.globalsolution.wellbeing_api.domain.model.enums.StatusConsulta;
import com.globalsolution.wellbeing_api.repository.ConsultaRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Serviço responsável pela lógica de negócio relacionada às consultas
 * entre pacientes e profissionais de saúde.
 */
@Service
@RequiredArgsConstructor
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final PacienteService pacienteService;
    private final ProfissionalSaudeService profissionalService;
    private final CadastroBasicoService cadastroBasicoService;

    /**
     * Lista todas as consultas cadastradas.
     */
    public List<Consulta> listar() {
        return cadastroBasicoService.listarTodos(consultaRepository);
    }

    /**
     * Lista consultas de um paciente específico.
     */
    public List<Consulta> listarPorPaciente(Long pacienteId) {
        return consultaRepository.findByPacienteId(pacienteId);
    }

    /**
     * Lista consultas de um profissional específico.
     */
    public List<Consulta> listarPorProfissional(Long profissionalId) {
        return consultaRepository.findByProfissionalId(profissionalId);
    }

    /**
     * Busca uma consulta por ID (ou falha).
     */
    public Consulta buscarOuFalhar(Long id) {
        return consultaRepository.findById(id)
                .orElseThrow(() -> new com.globalsolution.wellbeing_api.domain.exception.ConsultaNaoEncontradaException(id));
    }

    /**
     * Cria uma nova consulta garantindo regras importantes:
     * - paciente existe
     * - profissional existe
     * - data futura
     * - profissional não tem outra consulta no mesmo horário
     */
    @Transactional
    public Consulta cadastrar(Long pacienteId, Long profissionalId, Consulta consulta) {

        Paciente paciente = pacienteService.buscarOuFalhar(pacienteId);
        ProfissionalSaude profissional = profissionalService.buscarOuFalhar(profissionalId);

        validarDataFutura(consulta.getDataHora());
        validarConflitoHorario(profissionalId, consulta.getDataHora());

        consulta.setPaciente(paciente);
        consulta.setProfissional(profissional);
        consulta.setStatus(StatusConsulta.AGENDADA);

        return cadastroBasicoService.salvar(consultaRepository, consulta);
    }

    /**
     * Atualiza uma consulta já existente.
     */
    @Transactional
    public Consulta atualizar(Long id, Consulta dados) {
        Consulta atual = buscarOuFalhar(id);

        validarDataFutura(dados.getDataHora());
        validarConflitoHorario(atual.getProfissional().getId(), dados.getDataHora(), id);

        atual.setDataHora(dados.getDataHora());
        atual.setObservacao(dados.getObservacao());

        return cadastroBasicoService.salvar(consultaRepository, atual);
    }

    /**
     * Cancela uma consulta já agendada.
     */
    @Transactional
    public Consulta cancelar(Long id) {
        Consulta consulta = buscarOuFalhar(id);

        consulta.setStatus(StatusConsulta.CANCELADA);

        return cadastroBasicoService.salvar(consultaRepository, consulta);
    }

    /**
     * Marca uma consulta como realizada.
     */
    @Transactional
    public Consulta marcarRealizada(Long id) {
        Consulta consulta = buscarOuFalhar(id);

        consulta.setStatus(StatusConsulta.REALIZADA);

        return cadastroBasicoService.salvar(consultaRepository, consulta);
    }

    /**
     * Remove uma consulta do sistema.
     */
    @Transactional
    public void remover(Long id) {
        cadastroBasicoService.remover(consultaRepository, id);
    }

    /**
     * Valida se a data informada é no futuro.
     */
    private void validarDataFutura(LocalDateTime data) {
        if (data.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException(
                "A consulta não pode ser marcada para uma data passada."
            );
        }
    }

    /**
     * Valida conflito de horário para o profissional.
     * (Versão para criação)
     */
    private void validarConflitoHorario(Long profissionalId, LocalDateTime dataHora) {
        List<Consulta> consultas = consultaRepository.findByProfissionalId(profissionalId);

        boolean conflito = consultas.stream()
                .anyMatch(c -> c.getDataHora().equals(dataHora)
                        && c.getStatus() != StatusConsulta.CANCELADA);

        if (conflito) {
            throw new IllegalArgumentException(
                "O profissional já possui uma consulta neste horário."
            );
        }
    }

    /**
     * Valida conflito de horário para o profissional,
     * ignorando a própria consulta durante atualização.
     */
    private void validarConflitoHorario(Long profissionalId, LocalDateTime dataHora, Long consultaId) {
        List<Consulta> consultas = consultaRepository.findByProfissionalId(profissionalId);

        boolean conflito = consultas.stream()
                .anyMatch(c -> 
                        !c.getId().equals(consultaId) &&
                        c.getDataHora().equals(dataHora) &&
                        c.getStatus() != StatusConsulta.CANCELADA
                );

        if (conflito) {
            throw new IllegalArgumentException(
                "O profissional já possui outra consulta neste horário."
            );
        }
    }
}
