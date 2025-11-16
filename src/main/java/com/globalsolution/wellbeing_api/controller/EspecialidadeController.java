package com.globalsolution.wellbeing_api.controller;

import com.globalsolution.wellbeing_api.domain.dto.especialidade.EspecialidadeRequestDTO;
import com.globalsolution.wellbeing_api.domain.dto.especialidade.EspecialidadeResponseDTO;
import com.globalsolution.wellbeing_api.domain.model.Especialidade;
import com.globalsolution.wellbeing_api.mapper.EspecialidadeMapper;
import com.globalsolution.wellbeing_api.service.EspecialidadeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsável por expor endpoints REST
 * relacionados ao gerenciamento de especialidades.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/especialidades")
public class EspecialidadeController {

    private final EspecialidadeService especialidadeService;

    /**
     * Lista todas as especialidades cadastradas.
     */
    @GetMapping
    public List<EspecialidadeResponseDTO> listar() {
        return EspecialidadeMapper.toResponseList(especialidadeService.listar());
    }

    /**
     * Busca uma especialidade pelo ID.
     */
    @GetMapping("/{id}")
    public EspecialidadeResponseDTO buscar(@PathVariable Long id) {
        return EspecialidadeMapper.toResponse(especialidadeService.buscarOuFalhar(id));
    }

    /**
     * Cadastra uma nova especialidade.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EspecialidadeResponseDTO cadastrar(
            @RequestBody @Valid EspecialidadeRequestDTO dto) {
        Especialidade salvo = especialidadeService.cadastrar(EspecialidadeMapper.toEntity(dto));
        return EspecialidadeMapper.toResponse(salvo);
    }

    /**
     * Atualiza uma especialidade existente.
     */
    @PutMapping("/{id}")
    public EspecialidadeResponseDTO atualizar(
            @PathVariable Long id,
            @RequestBody @Valid EspecialidadeRequestDTO dto) {
        Especialidade atualizado = especialidadeService.atualizar(id, EspecialidadeMapper.toEntity(dto));
        return EspecialidadeMapper.toResponse(atualizado);
    }

    /**
     * Remove uma especialidade cadastrada.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        especialidadeService.remover(id);
    }
}
