package com.globalsolution.wellbeing_api.controller;

import com.globalsolution.wellbeing_api.domain.dto.profissional.ProfissionalSaudeRequestDTO;
import com.globalsolution.wellbeing_api.domain.dto.profissional.ProfissionalSaudeResponseDTO;
import com.globalsolution.wellbeing_api.domain.model.Especialidade;
import com.globalsolution.wellbeing_api.domain.model.ProfissionalSaude;
import com.globalsolution.wellbeing_api.mapper.ProfissionalSaudeMapper;
import com.globalsolution.wellbeing_api.service.EspecialidadeService;
import com.globalsolution.wellbeing_api.service.ProfissionalSaudeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller responsável pelos endpoints REST de Profissionais de Saúde.
 * Inclui CRUD e vínculo de Especialidades.
 */
@RestController
@RequestMapping("/profissionais")
@RequiredArgsConstructor
public class ProfissionalSaudeController {

    private final ProfissionalSaudeService profissionalService;
    private final EspecialidadeService especialidadeService;

    @GetMapping
    public List<ProfissionalSaudeResponseDTO> listar() {
        return profissionalService.listar().stream()
                .map(ProfissionalSaudeMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProfissionalSaudeResponseDTO buscar(@PathVariable Long id) {
        return ProfissionalSaudeMapper.toResponse(profissionalService.buscarOuFalhar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProfissionalSaudeResponseDTO cadastrar(@RequestBody @Valid ProfissionalSaudeRequestDTO dto) {
        ProfissionalSaude entity = ProfissionalSaudeMapper.toEntity(dto);
        if (dto.getEspecialidadesIds() != null) {
            List<Especialidade> especialidades = dto.getEspecialidadesIds().stream()
                    .map(especialidadeService::buscarOuFalhar)
                    .collect(Collectors.toList());
            entity.setEspecialidades(especialidades);
        }
        ProfissionalSaude salvo = profissionalService.cadastrar(entity);
        return ProfissionalSaudeMapper.toResponse(salvo);
    }

    @PutMapping("/{id}")
    public ProfissionalSaudeResponseDTO atualizar(@PathVariable Long id,
                                                  @RequestBody @Valid ProfissionalSaudeRequestDTO dto) {
        ProfissionalSaude dados = ProfissionalSaudeMapper.toEntity(dto);
        if (dto.getEspecialidadesIds() != null) {
            List<Especialidade> especialidades = dto.getEspecialidadesIds().stream()
                    .map(especialidadeService::buscarOuFalhar)
                    .collect(Collectors.toList());
            dados.setEspecialidades(especialidades);
        }
        ProfissionalSaude atualizado = profissionalService.atualizar(id, dados);
        return ProfissionalSaudeMapper.toResponse(atualizado);
    }

    @PostMapping("/{id}/especialidades/{especialidadeId}")
    public ProfissionalSaudeResponseDTO adicionarEspecialidade(@PathVariable Long id,
                                                               @PathVariable Long especialidadeId) {
        ProfissionalSaude atualizado = profissionalService.adicionarEspecialidade(id, especialidadeId);
        return ProfissionalSaudeMapper.toResponse(atualizado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        profissionalService.remover(id);
    }
}
