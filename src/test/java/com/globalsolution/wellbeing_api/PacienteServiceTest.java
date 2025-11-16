package com.globalsolution.wellbeing_api;

import com.globalsolution.wellbeing_api.domain.exception.PacienteNaoEncontradoException;
import com.globalsolution.wellbeing_api.domain.model.Paciente;
import com.globalsolution.wellbeing_api.repository.PacienteRepository;
import com.globalsolution.wellbeing_api.service.CadastroBasicoService;
import com.globalsolution.wellbeing_api.service.PacienteService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PacienteServiceTest {

	@Mock
	PacienteRepository pacienteRepository;

	@Mock
	CadastroBasicoService cadastroBasicoService;

	@InjectMocks
	PacienteService pacienteService;

	@Test
	void cadastrar_deveLancarErro_quandoCpfDuplicado() {
		var novo = new Paciente();
		novo.setCpf("123.456.789-00");

		when(pacienteRepository.findByCpf("123.456.789-00"))
				.thenReturn(Optional.of(new Paciente()));

		assertThrows(IllegalArgumentException.class, () -> pacienteService.cadastrar(novo));
	}

	@Test
	void buscarOuFalhar_deveLancarNotFound_quandoNaoExistir() {
		when(pacienteRepository.findById(99L)).thenReturn(Optional.empty());

		assertThrows(PacienteNaoEncontradoException.class, () -> pacienteService.buscarOuFalhar(99L));
	}

	@Test
	void cadastrar_deveSalvar_quandoDadosValidos() {
		var novo = new Paciente();
		novo.setCpf("987.654.321-00");

		when(pacienteRepository.findByCpf("987.654.321-00")).thenReturn(Optional.empty());

		var salvo = new Paciente();
		salvo.setId(1L);
		salvo.setCpf(novo.getCpf());

		when(cadastroBasicoService.salvar(any(), any())).thenReturn(salvo);

		var resultado = pacienteService.cadastrar(novo);
		assertNotNull(resultado.getId());
		assertEquals("987.654.321-00", resultado.getCpf());
	}
}
