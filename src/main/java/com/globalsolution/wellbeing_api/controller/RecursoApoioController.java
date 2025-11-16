package com.globalsolution.wellbeing_api.controller;

import com.globalsolution.wellbeing_api.domain.dto.recurso.RecursoApoioRequestDTO;
import com.globalsolution.wellbeing_api.domain.dto.recurso.RecursoApoioResponseDTO;
import com.globalsolution.wellbeing_api.domain.model.RecursoApoio;
import com.globalsolution.wellbeing_api.domain.model.enums.TipoRecurso;
import com.globalsolution.wellbeing_api.mapper.RecursoApoioMapper;
import com.globalsolution.wellbeing_api.service.RecursoApoioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller responsável pelos endpoints REST de Recursos de Apoio.
 * Suporta filtro por tipo de recurso.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/recursos")
public class RecursoApoioController {

    private final RecursoApoioService recursoService;

    @GetMapping
    public List<RecursoApoioResponseDTO> listar(@RequestParam(value = "tipo", required = false) TipoRecurso tipo) {
        List<RecursoApoio> recursos = (tipo == null)
                ? recursoService.listar()
                : recursoService.listarPorTipo(tipo);
        return recursos.stream()
                .map(RecursoApoioMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public RecursoApoioResponseDTO buscar(@PathVariable Long id) {
        return RecursoApoioMapper.toResponse(recursoService.buscarOuFalhar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RecursoApoioResponseDTO cadastrar(@RequestBody @Valid RecursoApoioRequestDTO dto,
                                             @RequestParam(value = "profissionalId", required = false) Long profissionalId) {
        RecursoApoio entity = RecursoApoioMapper.toEntity(dto);
        RecursoApoio salvo = recursoService.cadastrar(entity, profissionalId);
        return RecursoApoioMapper.toResponse(salvo);
    }

    @PutMapping("/{id}")
    public RecursoApoioResponseDTO atualizar(@PathVariable Long id,
                                             @RequestBody @Valid RecursoApoioRequestDTO dto,
                                             @RequestParam(value = "profissionalId", required = false) Long profissionalId) {
        RecursoApoio dados = RecursoApoioMapper.toEntity(dto);
        RecursoApoio atualizado = recursoService.atualizar(id, dados, profissionalId);
        return RecursoApoioMapper.toResponse(atualizado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        recursoService.remover(id);
    }
}
