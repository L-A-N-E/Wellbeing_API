package com.globalsolution.wellbeing_api.domain.exception;

public class ConsultaNaoEncontradaException extends EntidadeNaoEncontradaException {
	public ConsultaNaoEncontradaException(Long id) {
		super("Consulta não encontrada com ID: " + id);
	}
}
