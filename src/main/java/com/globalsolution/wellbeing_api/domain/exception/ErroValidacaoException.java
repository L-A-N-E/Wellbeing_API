package com.globalsolution.wellbeing_api.domain.exception;

import org.springframework.validation.BindingResult;

public class ErroValidacaoException extends NegocioException {
	private final transient BindingResult bindingResult;

	public ErroValidacaoException(String message, BindingResult bindingResult) {
		super(message);
		this.bindingResult = bindingResult;
	}

	public BindingResult getBindingResult() {
		return bindingResult;
	}
}
