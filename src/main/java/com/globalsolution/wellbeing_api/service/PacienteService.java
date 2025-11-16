package com.globalsolution.wellbeing_api.service;

import com.globalsolution.wellbeing_api.domain.model.Paciente;
import com.globalsolution.wellbeing_api.repository.PacienteRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Camada de serviço responsável por toda a lógica de negócio
 * relacionada aos pacientes.
 *
 * Aqui realizamos:
 * - validações
 * - regras de domínio
 * - chamadas aos repositórios
 * - uso dos métodos utilitários do CadastroBasicoService
 */
@Service
@RequiredArgsConstructor
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    private final CadastroBasicoService cadastroBasicoService;

    /**
     * Lista todos os pacientes cadastrados no sistema.
     *
     * @return lista de pacientes
     */
    public List<Paciente> listar() {
        return cadastroBasicoService.listarTodos(pacienteRepository);
    }

    /**
     * Busca um paciente pelo ID, ou lança exceção se não existir.
     *
     * @param id identificador
     * @return paciente encontrado
     */
    public Paciente buscarOuFalhar(Long id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new com.globalsolution.wellbeing_api.domain.exception.PacienteNaoEncontradoException(id));
    }

    /**
     * Cadastra um novo paciente, garantindo que:
     * - CPF não esteja duplicado
     *
     * @param paciente paciente a cadastrar
     * @return paciente salvo
     */
    @Transactional
    public Paciente cadastrar(Paciente paciente) {
        validarCpfUnico(paciente.getCpf());
        return cadastroBasicoService.salvar(pacienteRepository, paciente);
    }

    /**
     * Atualiza um paciente existente. Também verifica CPF duplicado,
     * mas permite que o próprio paciente mantenha seu CPF atual.
     *
     * @param id ID do paciente a atualizar
     * @param dados novos dados
     * @return paciente atualizado
     */
    @Transactional
    public Paciente atualizar(Long id, Paciente dados) {
        Paciente pacienteAtual = buscarOuFalhar(id);

        // Se o CPF foi alterado, validar se não existe outro igual
        if (!pacienteAtual.getCpf().equals(dados.getCpf())) {
            validarCpfUnico(dados.getCpf());
        }

        pacienteAtual.setNome(dados.getNome());
        pacienteAtual.setCpf(dados.getCpf());
        pacienteAtual.setEmail(dados.getEmail());

        return cadastroBasicoService.salvar(pacienteRepository, pacienteAtual);
    }

    /**
     * Remove um paciente do sistema.
     *
     * @param id identificador
     */
    @Transactional
    public void remover(Long id) {
        cadastroBasicoService.remover(pacienteRepository, id);
    }

    /**
     * Valida se já existe um paciente com o CPF informado.
     *
     * @param cpf cpf a validar
     */
    private void validarCpfUnico(String cpf) {
        boolean existe = pacienteRepository.findByCpf(cpf).isPresent();
        if (existe) {
            throw new IllegalArgumentException(
                "Já existe um paciente cadastrado com o CPF: " + cpf
            );
        }
    }
}
