package com.globalsolution.wellbeing_api.mapper;

import com.globalsolution.wellbeing_api.domain.dto.especialidade.EspecialidadeRequestDTO;
import com.globalsolution.wellbeing_api.domain.dto.especialidade.EspecialidadeResponseDTO;
import com.globalsolution.wellbeing_api.domain.model.Especialidade;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper responsável por converter Especialidade entre Entidade e DTOs.
 * Oferece mapeamentos de RequestDTO -> Entidade e Entidade -> ResponseDTO.
 */
public class EspecialidadeMapper {

	/**
	 * Converte {@link com.globalsolution.wellbeing_api.domain.dto.especialidade.EspecialidadeRequestDTO}
	 * em entidade {@link com.globalsolution.wellbeing_api.domain.model.Especialidade}.
	 * Retorna null quando o DTO é null.
	 */
	public static Especialidade toEntity(EspecialidadeRequestDTO dto) {
		if (dto == null) return null;
		Especialidade e = new Especialidade();
		e.setNome(dto.getNome());
		e.setDescricao(dto.getDescricao());
		return e;
	}

	/**
	 * Converte a entidade Especialidade para
	 * {@link com.globalsolution.wellbeing_api.domain.dto.especialidade.EspecialidadeResponseDTO}.
	 * Retorna null quando a entidade é null.
	 */
	public static EspecialidadeResponseDTO toResponse(Especialidade entity) {
		if (entity == null) return null;
		EspecialidadeResponseDTO dto = new EspecialidadeResponseDTO();
		dto.setId(entity.getId());
		dto.setNome(entity.getNome());
		dto.setDescricao(entity.getDescricao());
		return dto;
	}

	/**
	 * Converte uma lista de entidades Especialidade em lista de ResponseDTO.
	 */
	public static List<EspecialidadeResponseDTO> toResponseList(List<Especialidade> list) {
		return list.stream().map(EspecialidadeMapper::toResponse).collect(Collectors.toList());
	}
}
