package com.globalsolution.wellbeing_api.domain.exception;

public class PacienteNaoEncontradoException extends EntidadeNaoEncontradaException {
	public PacienteNaoEncontradoException(Long id) {
		super("Paciente não encontrado com ID: " + id);
	}
}
