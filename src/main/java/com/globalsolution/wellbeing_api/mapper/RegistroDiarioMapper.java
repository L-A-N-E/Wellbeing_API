package com.globalsolution.wellbeing_api.mapper;

import com.globalsolution.wellbeing_api.domain.dto.registrodiario.RegistroDiarioRequestDTO;
import com.globalsolution.wellbeing_api.domain.dto.registrodiario.RegistroDiarioResponseDTO;
import com.globalsolution.wellbeing_api.domain.model.RegistroDiario;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper responsável por converter Registro Diário entre Entidade e DTOs.
 */
public class RegistroDiarioMapper {

	/**
	 * Converte {@link com.globalsolution.wellbeing_api.domain.dto.registrodiario.RegistroDiarioRequestDTO}
	 * em entidade {@link com.globalsolution.wellbeing_api.domain.model.RegistroDiario}.
	 * Retorna null quando o DTO é null.
	 */
	public static RegistroDiario toEntity(RegistroDiarioRequestDTO dto) {
		if (dto == null) return null;
		RegistroDiario r = new RegistroDiario();
		r.setDataHora(dto.getDataHora());
		r.setHumor(dto.getHumor());
		r.setEmocao(dto.getEmocao());
		r.setObservacao(dto.getObservacao());
		return r;
	}

	/**
	 * Converte a entidade RegistroDiario para
	 * {@link com.globalsolution.wellbeing_api.domain.dto.registrodiario.RegistroDiarioResponseDTO}.
	 * Retorna null quando a entidade é null.
	 */
	public static RegistroDiarioResponseDTO toResponse(RegistroDiario entity) {
		if (entity == null) return null;
		RegistroDiarioResponseDTO dto = new RegistroDiarioResponseDTO();
		dto.setId(entity.getId());
		dto.setDataHora(entity.getDataHora());
		dto.setHumor(entity.getHumor());
		dto.setEmocao(entity.getEmocao());
		dto.setObservacao(entity.getObservacao());
		return dto;
	}

	/**
	 * Converte uma lista de entidades RegistroDiario em lista de ResponseDTO.
	 */
	public static List<RegistroDiarioResponseDTO> toResponseList(List<RegistroDiario> list) {
		return list.stream().map(RegistroDiarioMapper::toResponse).collect(Collectors.toList());
	}
}
