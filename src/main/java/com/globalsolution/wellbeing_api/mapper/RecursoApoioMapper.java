package com.globalsolution.wellbeing_api.mapper;

import com.globalsolution.wellbeing_api.domain.dto.recurso.RecursoApoioRequestDTO;
import com.globalsolution.wellbeing_api.domain.dto.recurso.RecursoApoioResponseDTO;
import com.globalsolution.wellbeing_api.domain.model.RecursoApoio;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper responsável por converter Recurso de Apoio entre Entidade e DTOs.
 */
public class RecursoApoioMapper {

	/**
	 * Converte {@link com.globalsolution.wellbeing_api.domain.dto.recurso.RecursoApoioRequestDTO}
	 * em entidade {@link com.globalsolution.wellbeing_api.domain.model.RecursoApoio}.
	 * Retorna null quando o DTO é null.
	 */
	public static RecursoApoio toEntity(RecursoApoioRequestDTO dto) {
		if (dto == null) return null;
		RecursoApoio r = new RecursoApoio();
		r.setTitulo(dto.getTitulo());
		r.setDescricao(dto.getDescricao());
		r.setTipo(dto.getTipo());
		r.setUrl(dto.getUrl());
		return r;
	}

	/**
	 * Converte a entidade RecursoApoio para
	 * {@link com.globalsolution.wellbeing_api.domain.dto.recurso.RecursoApoioResponseDTO}.
	 * Retorna null quando a entidade é null.
	 */
	public static RecursoApoioResponseDTO toResponse(RecursoApoio entity) {
		if (entity == null) return null;
		RecursoApoioResponseDTO dto = new RecursoApoioResponseDTO();
		dto.setId(entity.getId());
		dto.setTitulo(entity.getTitulo());
		dto.setDescricao(entity.getDescricao());
		dto.setTipo(entity.getTipo());
		dto.setUrl(entity.getUrl());
		return dto;
	}

	/**
	 * Converte uma lista de entidades RecursoApoio em lista de ResponseDTO.
	 */
	public static List<RecursoApoioResponseDTO> toResponseList(List<RecursoApoio> list) {
		return list.stream().map(RecursoApoioMapper::toResponse).collect(Collectors.toList());
	}
}
