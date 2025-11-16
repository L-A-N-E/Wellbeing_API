package com.globalsolution.wellbeing_api.controller;

import com.globalsolution.wellbeing_api.domain.dto.condicao.CondicaoPacienteRequestDTO;
import com.globalsolution.wellbeing_api.domain.dto.condicao.CondicaoPacienteResponseDTO;
import com.globalsolution.wellbeing_api.domain.model.CondicaoPaciente;
import com.globalsolution.wellbeing_api.mapper.CondicaoPacienteMapper;
import com.globalsolution.wellbeing_api.service.CondicaoPacienteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller responsável pelos endpoints REST de Condições do Paciente.
 * Permite CRUD vinculado a paciente e acesso direto por ID.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping
public class CondicaoPacienteController {

    private final CondicaoPacienteService condicaoService;

    @GetMapping("/pacientes/{pacienteId}/condicoes")
    public List<CondicaoPacienteResponseDTO> listarPorPaciente(@PathVariable Long pacienteId) {
        return condicaoService.listarPorPaciente(pacienteId).stream()
                .map(CondicaoPacienteMapper::toResponse)
                .collect(Collectors.toList());
    }

    @PostMapping("/pacientes/{pacienteId}/condicoes")
    @ResponseStatus(HttpStatus.CREATED)
    public CondicaoPacienteResponseDTO cadastrar(@PathVariable Long pacienteId,
                                                 @RequestBody @Valid CondicaoPacienteRequestDTO dto) {
        CondicaoPaciente entity = CondicaoPacienteMapper.toEntity(dto);
        CondicaoPaciente salvo = condicaoService.cadastrar(pacienteId, entity);
        return CondicaoPacienteMapper.toResponse(salvo);
    }

    @GetMapping("/condicoes/{id}")
    public CondicaoPacienteResponseDTO buscar(@PathVariable Long id) {
        return CondicaoPacienteMapper.toResponse(condicaoService.buscarOuFalhar(id));
    }

    @PutMapping("/condicoes/{id}")
    public CondicaoPacienteResponseDTO atualizar(@PathVariable Long id,
                                                 @RequestBody @Valid CondicaoPacienteRequestDTO dto) {
        CondicaoPaciente dados = CondicaoPacienteMapper.toEntity(dto);
        CondicaoPaciente atualizada = condicaoService.atualizar(id, dados);
        return CondicaoPacienteMapper.toResponse(atualizada);
    }

    @DeleteMapping("/condicoes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        condicaoService.remover(id);
    }
}
