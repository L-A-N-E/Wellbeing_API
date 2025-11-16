package com.globalsolution.wellbeing_api;

import com.globalsolution.wellbeing_api.domain.model.Paciente;
import com.globalsolution.wellbeing_api.domain.model.RegistroDiario;
import com.globalsolution.wellbeing_api.repository.RegistroDiarioRepository;
import com.globalsolution.wellbeing_api.service.CadastroBasicoService;
import com.globalsolution.wellbeing_api.service.PacienteService;
import com.globalsolution.wellbeing_api.service.RegistroDiarioService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegistroDiarioServiceTest {

	@Mock RegistroDiarioRepository registroRepository;
	@Mock PacienteService pacienteService;
	@Mock CadastroBasicoService cadastroBasicoService;

	@InjectMocks RegistroDiarioService registroService;

	private Paciente pac() { var p = new Paciente(); p.setId(1L); return p; }

	@Test
	void cadastrar_deveVincularPacienteESalvar() {
		var reg = new RegistroDiario();
		reg.setHumor(8);
		reg.setDataHora(LocalDateTime.now());

		when(pacienteService.buscarOuFalhar(1L)).thenReturn(pac());
		when(cadastroBasicoService.salvar(any(), any())).thenAnswer(inv -> inv.getArgument(1));

		var salvo = registroService.cadastrar(1L, reg);
		assertNotNull(salvo);
		assertEquals(8, salvo.getHumor());
		assertNotNull(salvo.getPaciente());
	}

	@Test
	void atualizar_deveAlterarCamposBasicos() {
		var atual = new RegistroDiario(); atual.setId(2L);
		var novos = new RegistroDiario();
		novos.setHumor(5); novos.setEmocao("ansioso"); novos.setObservacao("anotacao");
		novos.setDataHora(LocalDateTime.now());

		when(cadastroBasicoService.buscarOuFalhar(any(), any())).thenReturn(atual);
		when(cadastroBasicoService.salvar(any(), any())).thenAnswer(inv -> inv.getArgument(1));

		var res = registroService.atualizar(2L, novos);
		assertEquals(5, res.getHumor());
		assertEquals("ansioso", res.getEmocao());
		assertEquals("anotacao", res.getObservacao());
	}

	@Test
	void listarPorPaciente_deveRetornarRegistros() {
		when(registroRepository.findByPacienteId(1L)).thenReturn(List.of(new RegistroDiario()));
		var list = registroService.listarPorPaciente(1L);
		assertFalse(list.isEmpty());
	}
}
