package com.globalsolution.wellbeing_api.mapper;

import com.globalsolution.wellbeing_api.domain.dto.condicao.CondicaoPacienteRequestDTO;
import com.globalsolution.wellbeing_api.domain.dto.condicao.CondicaoPacienteResponseDTO;
import com.globalsolution.wellbeing_api.domain.model.CondicaoPaciente;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper responsável por converter Condição do Paciente entre Entidade e DTOs.
 */
public class CondicaoPacienteMapper {

	/**
	 * Converte {@link com.globalsolution.wellbeing_api.domain.dto.condicao.CondicaoPacienteRequestDTO}
	 * em entidade {@link com.globalsolution.wellbeing_api.domain.model.CondicaoPaciente}.
	 * Retorna null quando o DTO é null.
	 */
	public static CondicaoPaciente toEntity(CondicaoPacienteRequestDTO dto) {
		if (dto == null) return null;
		CondicaoPaciente c = new CondicaoPaciente();
		c.setNome(dto.getNome());
		c.setDescricao(dto.getDescricao());
		c.setSeveridade(dto.getSeveridade());
		c.setDataDiagnostico(dto.getDataDiagnostico());
		return c;
	}

	/**
	 * Converte a entidade CondicaoPaciente para
	 * {@link com.globalsolution.wellbeing_api.domain.dto.condicao.CondicaoPacienteResponseDTO}.
	 * Retorna null quando a entidade é null.
	 */
	public static CondicaoPacienteResponseDTO toResponse(CondicaoPaciente entity) {
		if (entity == null) return null;
		CondicaoPacienteResponseDTO dto = new CondicaoPacienteResponseDTO();
		dto.setId(entity.getId());
		dto.setNome(entity.getNome());
		dto.setDescricao(entity.getDescricao());
		dto.setSeveridade(entity.getSeveridade());
		dto.setDataDiagnostico(entity.getDataDiagnostico());
		return dto;
	}

	/**
	 * Converte uma lista de entidades CondicaoPaciente em lista de ResponseDTO.
	 */
	public static List<CondicaoPacienteResponseDTO> toResponseList(List<CondicaoPaciente> list) {
		return list.stream().map(CondicaoPacienteMapper::toResponse).collect(Collectors.toList());
	}
}
