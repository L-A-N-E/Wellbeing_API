package com.globalsolution.wellbeing_api.mapper;

import com.globalsolution.wellbeing_api.domain.dto.paciente.PacienteRequestDTO;
import com.globalsolution.wellbeing_api.domain.dto.paciente.PacienteResponseDTO;
import com.globalsolution.wellbeing_api.domain.model.Paciente;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper responsável por converter Paciente entre Entidade e DTOs.
 * Fornece métodos para mapear RequestDTO -> Entidade e Entidade -> ResponseDTO.
 */
public class PacienteMapper {

	/**
	 * Converte um {@link com.globalsolution.wellbeing_api.domain.dto.paciente.PacienteRequestDTO}
	 * em entidade {@link com.globalsolution.wellbeing_api.domain.model.Paciente}.
	 * Retorna null quando o DTO é null.
	 */
	public static Paciente toEntity(PacienteRequestDTO dto) {
		if (dto == null) return null;
		Paciente p = new Paciente();
		p.setNome(dto.getNome());
		p.setCpf(dto.getCpf());
		p.setEmail(dto.getEmail());
		p.setDataNascimento(dto.getDataNascimento());
		return p;
	}

	/**
	 * Converte a entidade {@link com.globalsolution.wellbeing_api.domain.model.Paciente}
	 * para {@link com.globalsolution.wellbeing_api.domain.dto.paciente.PacienteResponseDTO}.
	 * Retorna null quando a entidade é null.
	 */
	public static PacienteResponseDTO toResponse(Paciente entity) {
		if (entity == null) return null;
		PacienteResponseDTO dto = new PacienteResponseDTO();
		dto.setId(entity.getId());
		dto.setNome(entity.getNome());
		dto.setCpf(entity.getCpf());
		dto.setEmail(entity.getEmail());
		dto.setDataNascimento(entity.getDataNascimento());
		return dto;
	}

	/**
	 * Converte uma lista de entidades Paciente em lista de ResponseDTO.
	 */
	public static List<PacienteResponseDTO> toResponseList(List<Paciente> list) {
		return list.stream().map(PacienteMapper::toResponse).collect(Collectors.toList());
	}
}
