package com.globalsolution.wellbeing_api.mapper;

import com.globalsolution.wellbeing_api.domain.dto.consulta.ConsultaRequestDTO;
import com.globalsolution.wellbeing_api.domain.dto.consulta.ConsultaResponseDTO;
import com.globalsolution.wellbeing_api.domain.model.Consulta;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper responsável por converter Consulta entre Entidade e DTOs.
 * Inclui composição de Paciente e Profissional no ResponseDTO.
 */
public class ConsultaMapper {

	/**
	 * Converte {@link com.globalsolution.wellbeing_api.domain.dto.consulta.ConsultaRequestDTO}
	 * em entidade {@link com.globalsolution.wellbeing_api.domain.model.Consulta}.
	 * Retorna null quando o DTO é null.
	 */
	public static Consulta toEntity(ConsultaRequestDTO dto) {
		if (dto == null) return null;
		Consulta c = new Consulta();
		c.setDataHora(dto.getDataHora());
		c.setObservacao(dto.getObservacao());
		return c;
	}

	/**
	 * Converte a entidade Consulta para
	 * {@link com.globalsolution.wellbeing_api.domain.dto.consulta.ConsultaResponseDTO},
	 * mapeando também Paciente e Profissional relacionados.
	 * Retorna null quando a entidade é null.
	 */
	public static ConsultaResponseDTO toResponse(Consulta entity) {
		if (entity == null) return null;
		ConsultaResponseDTO dto = new ConsultaResponseDTO();
		dto.setId(entity.getId());
		dto.setDataHora(entity.getDataHora());
		dto.setStatus(entity.getStatus());
		dto.setObservacao(entity.getObservacao());
		dto.setPaciente(PacienteMapper.toResponse(entity.getPaciente()));
		dto.setProfissional(ProfissionalSaudeMapper.toResponse(entity.getProfissional()));
		return dto;
	}

	/**
	 * Converte uma lista de entidades Consulta em lista de ResponseDTO.
	 */
	public static List<ConsultaResponseDTO> toResponseList(List<Consulta> list) {
		return list.stream().map(ConsultaMapper::toResponse).collect(Collectors.toList());
	}
}
