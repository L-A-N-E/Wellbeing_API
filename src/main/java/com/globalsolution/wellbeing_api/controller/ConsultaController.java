package com.globalsolution.wellbeing_api.controller;

import com.globalsolution.wellbeing_api.domain.dto.consulta.ConsultaRequestDTO;
import com.globalsolution.wellbeing_api.domain.dto.consulta.ConsultaResponseDTO;
import com.globalsolution.wellbeing_api.domain.model.Consulta;
import com.globalsolution.wellbeing_api.mapper.ConsultaMapper;
import com.globalsolution.wellbeing_api.service.ConsultaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller responsável pelos endpoints REST de Consultas.
 * Contempla CRUD, listagens por paciente/profissional e ações (cancelar/realizar).
 */
@RestController
@RequiredArgsConstructor
@RequestMapping
public class ConsultaController {

    private final ConsultaService consultaService;

    @GetMapping("/consultas")
    public List<ConsultaResponseDTO> listar() {
        return consultaService.listar().stream()
                .map(ConsultaMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/consultas/{id}")
    public ConsultaResponseDTO buscarPorId(@PathVariable Long id) {
        return ConsultaMapper.toResponse(consultaService.buscarOuFalhar(id));
    }

    @GetMapping("/pacientes/{pacienteId}/consultas")
    public List<ConsultaResponseDTO> listarPorPaciente(@PathVariable Long pacienteId) {
        return consultaService.listarPorPaciente(pacienteId).stream()
                .map(ConsultaMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/profissionais/{profissionalId}/consultas")
    public List<ConsultaResponseDTO> listarPorProfissional(@PathVariable Long profissionalId) {
        return consultaService.listarPorProfissional(profissionalId).stream()
                .map(ConsultaMapper::toResponse)
                .collect(Collectors.toList());
    }

    @PostMapping("/consultas")
    @ResponseStatus(HttpStatus.CREATED)
    public ConsultaResponseDTO cadastrar(@RequestBody @Valid ConsultaRequestDTO dto) {
        Consulta entity = ConsultaMapper.toEntity(dto);
        Consulta salvo = consultaService.cadastrar(dto.getPacienteId(), dto.getProfissionalId(), entity);
        return ConsultaMapper.toResponse(salvo);
    }

    @PutMapping("/consultas/{id}")
    public ConsultaResponseDTO atualizar(@PathVariable Long id,
                                         @RequestBody @Valid ConsultaRequestDTO dto) {
        Consulta dados = ConsultaMapper.toEntity(dto);
        Consulta atualizado = consultaService.atualizar(id, dados);
        return ConsultaMapper.toResponse(atualizado);
    }

    @PostMapping("/consultas/{id}/cancelar")
    public ConsultaResponseDTO cancelar(@PathVariable Long id) {
        return ConsultaMapper.toResponse(consultaService.cancelar(id));
    }

    @PostMapping("/consultas/{id}/realizar")
    public ConsultaResponseDTO marcarRealizada(@PathVariable Long id) {
        return ConsultaMapper.toResponse(consultaService.marcarRealizada(id));
    }

    @DeleteMapping("/consultas/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        consultaService.remover(id);
    }
}
