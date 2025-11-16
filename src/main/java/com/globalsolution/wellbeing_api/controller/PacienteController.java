package com.globalsolution.wellbeing_api.controller;

import com.globalsolution.wellbeing_api.domain.dto.paciente.PacienteRequestDTO;
import com.globalsolution.wellbeing_api.domain.dto.paciente.PacienteResponseDTO;
import com.globalsolution.wellbeing_api.domain.model.Paciente;
import com.globalsolution.wellbeing_api.mapper.PacienteMapper;
import com.globalsolution.wellbeing_api.service.PacienteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsável por expor endpoints REST relacionados
 * ao gerenciamento de pacientes.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    /**
     * Lista todos os pacientes cadastrados.
     */
    @GetMapping
    public List<PacienteResponseDTO> listar() {
        return PacienteMapper.toResponseList(pacienteService.listar());
    }

    /**
     * Busca um paciente pelo ID.
     */
    @GetMapping("/{id}")
    public PacienteResponseDTO buscar(@PathVariable Long id) {
        Paciente paciente = pacienteService.buscarOuFalhar(id);
        return PacienteMapper.toResponse(paciente);
    }

    /**
     * Cadastra um novo paciente.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PacienteResponseDTO cadastrar(
            @RequestBody @Valid PacienteRequestDTO dto
    ) {
        Paciente paciente = PacienteMapper.toEntity(dto);
        Paciente salvo = pacienteService.cadastrar(paciente);
        return PacienteMapper.toResponse(salvo);
    }

    /**
     * Atualiza dados de um paciente existente.
     */
    @PutMapping("/{id}")
    public PacienteResponseDTO atualizar(
            @PathVariable Long id,
            @RequestBody @Valid PacienteRequestDTO dto
    ) {
        Paciente atualizado = pacienteService.atualizar(id, PacienteMapper.toEntity(dto));
        return PacienteMapper.toResponse(atualizado);
    }

    /**
     * Remove um paciente.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        pacienteService.remover(id);
    }

    // Conversões centralizadas no PacienteMapper
}
