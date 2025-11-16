package com.globalsolution.wellbeing_api.mapper;

import com.globalsolution.wellbeing_api.domain.dto.especialidade.EspecialidadeResponseDTO;
import com.globalsolution.wellbeing_api.domain.dto.profissional.ProfissionalSaudeRequestDTO;
import com.globalsolution.wellbeing_api.domain.dto.profissional.ProfissionalSaudeResponseDTO;
import com.globalsolution.wellbeing_api.domain.model.Especialidade;
import com.globalsolution.wellbeing_api.domain.model.ProfissionalSaude;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper responsável por converter Profissional de Saúde entre Entidade e DTOs.
 * Inclui mapeamento das Especialidades associadas.
 */
public class ProfissionalSaudeMapper {

	/**
	 * Converte {@link com.globalsolution.wellbeing_api.domain.dto.profissional.ProfissionalSaudeRequestDTO}
	 * em entidade {@link com.globalsolution.wellbeing_api.domain.model.ProfissionalSaude}.
	 * Retorna null quando o DTO é null.
	 */
	public static ProfissionalSaude toEntity(ProfissionalSaudeRequestDTO dto) {
		if (dto == null) return null;
		ProfissionalSaude p = new ProfissionalSaude();
		p.setNome(dto.getNome());
		p.setEmail(dto.getEmail());
		p.setRegistroProfissional(dto.getRegistroProfissional());
		return p;
	}

	/**
	 * Converte a entidade ProfissionalSaude para
	 * {@link com.globalsolution.wellbeing_api.domain.dto.profissional.ProfissionalSaudeResponseDTO},
	 * incluindo especialidades vinculadas.
	 * Retorna null quando a entidade é null.
	 */
	public static ProfissionalSaudeResponseDTO toResponse(ProfissionalSaude entity) {
		if (entity == null) return null;
		ProfissionalSaudeResponseDTO dto = new ProfissionalSaudeResponseDTO();
		dto.setId(entity.getId());
		dto.setNome(entity.getNome());
		dto.setEmail(entity.getEmail());
		dto.setRegistroProfissional(entity.getRegistroProfissional());

		List<Especialidade> especialidades = entity.getEspecialidades();
		if (especialidades != null) {
			List<EspecialidadeResponseDTO> esp = especialidades.stream()
					.map(EspecialidadeMapper::toResponse)
					.collect(Collectors.toList());
			dto.setEspecialidades(esp);
		}
		return dto;
	}

	/**
	 * Converte uma lista de entidades ProfissionalSaude em lista de ResponseDTO.
	 */
	public static List<ProfissionalSaudeResponseDTO> toResponseList(List<ProfissionalSaude> list) {
		return list.stream().map(ProfissionalSaudeMapper::toResponse).collect(Collectors.toList());
	}
}
