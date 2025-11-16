package com.globalsolution.wellbeing_api.controller;

import com.globalsolution.wellbeing_api.domain.dto.registrodiario.RegistroDiarioRequestDTO;
import com.globalsolution.wellbeing_api.domain.dto.registrodiario.RegistroDiarioResponseDTO;
import com.globalsolution.wellbeing_api.mapper.RegistroDiarioMapper;
import com.globalsolution.wellbeing_api.service.RegistroDiarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller responsável por expor endpoints REST para
 * gerenciamento de registros diários dos pacientes.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping
public class RegistroDiarioController {

    private final RegistroDiarioService registroService;

    // ------------------------------------------------------------
    // CRUD vinculado ao Paciente
    // ------------------------------------------------------------

    /**
     * Lista todos os registros diários pertencentes a um paciente.
     */
    @GetMapping("/pacientes/{pacienteId}/registros")
    public List<RegistroDiarioResponseDTO> listarPorPaciente(
            @PathVariable Long pacienteId) {
        return registroService.listarPorPaciente(pacienteId).stream()
            .map(RegistroDiarioMapper::toResponse)
            .collect(Collectors.toList());
    }

    /**
     * Cadastra um novo registro diário para um paciente.
     */
    @PostMapping("/pacientes/{pacienteId}/registros")
    @ResponseStatus(HttpStatus.CREATED)
    public RegistroDiarioResponseDTO cadastrar(
            @PathVariable Long pacienteId,
            @RequestBody @Valid RegistroDiarioRequestDTO dto) {
        var entity = RegistroDiarioMapper.toEntity(dto);
        var salvo = registroService.cadastrar(pacienteId, entity);
        return RegistroDiarioMapper.toResponse(salvo);
    }

    // ------------------------------------------------------------
    // CRUD direto por ID
    // ------------------------------------------------------------

    /**
     * Busca um registro diário pelo ID.
     */
    @GetMapping("/registros/{id}")
    public RegistroDiarioResponseDTO buscar(@PathVariable Long id) {
        return RegistroDiarioMapper.toResponse(registroService.buscarOuFalhar(id));
    }

    /**
     * Atualiza um registro diário.
     */
    @PutMapping("/registros/{id}")
    public RegistroDiarioResponseDTO atualizar(
            @PathVariable Long id,
            @RequestBody @Valid RegistroDiarioRequestDTO dto) {
        var dados = RegistroDiarioMapper.toEntity(dto);
        var atualizado = registroService.atualizar(id, dados);
        return RegistroDiarioMapper.toResponse(atualizado);
    }

    /**
     * Remove um registro diário.
     */
    @DeleteMapping("/registros/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        registroService.remover(id);
    }

    // Mapeamento movido para RegistroDiarioMapper
}
