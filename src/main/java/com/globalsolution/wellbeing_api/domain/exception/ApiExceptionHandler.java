package com.globalsolution.wellbeing_api.domain.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler {

	private Map<String, Object> baseBody(HttpStatus status, String message, WebRequest request) {
		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", OffsetDateTime.now());
		body.put("status", status.value());
		body.put("error", status.getReasonPhrase());
		body.put("message", message);
		if (request instanceof ServletWebRequest swr) {
			body.put("path", swr.getRequest().getRequestURI());
		}
		return body;
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex, WebRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		return ResponseEntity.status(status).body(baseBody(status, ex.getMessage(), request));
	}

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<Object> handleDomainNotFound(EntidadeNaoEncontradaException ex, WebRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		return ResponseEntity.status(status).body(baseBody(status, ex.getMessage(), request));
	}

	@ExceptionHandler({ IllegalArgumentException.class, NegocioException.class })
	public ResponseEntity<Object> handleBadRequest(RuntimeException ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		return ResponseEntity.status(status).body(baseBody(status, ex.getMessage(), request));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		Map<String, Object> body = baseBody(status, "Erro de validação", request);
		List<Map<String, String>> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
				.map(this::mapFieldError)
				.collect(Collectors.toList());
		body.put("fieldErrors", fieldErrors);
		return ResponseEntity.status(status).body(body);
	}

	private Map<String, String> mapFieldError(FieldError fe) {
		Map<String, String> m = new HashMap<>();
		m.put("field", fe.getField());
		m.put("message", fe.getDefaultMessage());
		return m;
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		return ResponseEntity.status(status).body(baseBody(status, ex.getMessage(), request));
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Object> handleDataIntegrity(DataIntegrityViolationException ex, WebRequest request) {
		HttpStatus status = HttpStatus.CONFLICT;
		return ResponseEntity.status(status).body(baseBody(status, "Violação de integridade de dados", request));
	}

	@ExceptionHandler({ HttpMessageNotReadableException.class, HttpRequestMethodNotSupportedException.class })
	public ResponseEntity<Object> handleClientErrors(Exception ex, WebRequest request) {
		HttpStatus status = ex instanceof HttpRequestMethodNotSupportedException ? HttpStatus.METHOD_NOT_ALLOWED : HttpStatus.BAD_REQUEST;
		return ResponseEntity.status(status).body(baseBody(status, ex.getMessage(), request));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		return ResponseEntity.status(status).body(baseBody(status, "Erro interno do servidor", request));
	}
}
