package com.globalsolution.wellbeing_api;

import com.globalsolution.wellbeing_api.domain.model.Consulta;
import com.globalsolution.wellbeing_api.domain.model.Paciente;
import com.globalsolution.wellbeing_api.domain.model.ProfissionalSaude;
import com.globalsolution.wellbeing_api.domain.model.enums.StatusConsulta;
import com.globalsolution.wellbeing_api.repository.ConsultaRepository;
import com.globalsolution.wellbeing_api.service.CadastroBasicoService;
import com.globalsolution.wellbeing_api.service.ConsultaService;
import com.globalsolution.wellbeing_api.service.PacienteService;
import com.globalsolution.wellbeing_api.service.ProfissionalSaudeService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultaServiceTest {

	@Mock ConsultaRepository consultaRepository;
	@Mock PacienteService pacienteService;
	@Mock ProfissionalSaudeService profissionalService;
	@Mock CadastroBasicoService cadastroBasicoService;

	@InjectMocks ConsultaService consultaService;

	private Paciente pac() { var p = new Paciente(); p.setId(1L); return p; }
	private ProfissionalSaude prof() { var pr = new ProfissionalSaude(); pr.setId(10L); return pr; }

	@Test
	void cadastrar_deveFalhar_quandoDataPassada() {
		var dto = new Consulta();
		dto.setDataHora(LocalDateTime.now().minusDays(1));

		when(pacienteService.buscarOuFalhar(1L)).thenReturn(pac());
		when(profissionalService.buscarOuFalhar(10L)).thenReturn(prof());

		assertThrows(IllegalArgumentException.class,
				() -> consultaService.cadastrar(1L, 10L, dto));
	}

	@Test
	void cadastrar_deveFalhar_quandoConflitoHorario() {
		var horario = LocalDateTime.now().plusDays(1).withSecond(0).withNano(0);
		var existente = new Consulta(); existente.setId(5L); existente.setDataHora(horario); existente.setStatus(StatusConsulta.AGENDADA);

		var dto = new Consulta(); dto.setDataHora(horario);

		when(pacienteService.buscarOuFalhar(1L)).thenReturn(pac());
		when(profissionalService.buscarOuFalhar(10L)).thenReturn(prof());
		when(consultaRepository.findByProfissionalId(10L)).thenReturn(List.of(existente));

		assertThrows(IllegalArgumentException.class,
				() -> consultaService.cadastrar(1L, 10L, dto));
	}

	@Test
	void cadastrar_deveSalvar_quandoValido() {
		var horario = LocalDateTime.now().plusDays(2);
		var dto = new Consulta(); dto.setDataHora(horario);

		when(pacienteService.buscarOuFalhar(1L)).thenReturn(pac());
		when(profissionalService.buscarOuFalhar(10L)).thenReturn(prof());
		when(consultaRepository.findByProfissionalId(10L)).thenReturn(List.of());

		var salvo = new Consulta(); salvo.setId(100L); salvo.setDataHora(horario); salvo.setStatus(StatusConsulta.AGENDADA);
		when(cadastroBasicoService.salvar(any(), any())).thenReturn(salvo);

		var res = consultaService.cadastrar(1L, 10L, dto);
		assertEquals(100L, res.getId());
		assertEquals(StatusConsulta.AGENDADA, res.getStatus());
	}

	@Test
	void cancelar_deveAtualizarStatus_paraCancelada() {
		var existente = new Consulta(); existente.setId(7L); existente.setStatus(StatusConsulta.AGENDADA);
		when(consultaRepository.findById(7L)).thenReturn(Optional.of(existente));
		when(cadastroBasicoService.salvar(any(), any())).thenAnswer(inv -> inv.getArgument(1));

		var res = consultaService.cancelar(7L);
		assertEquals(StatusConsulta.CANCELADA, res.getStatus());
	}

	@Test
	void marcarRealizada_deveAtualizarStatus_paraRealizada() {
		var existente = new Consulta(); existente.setId(8L); existente.setStatus(StatusConsulta.AGENDADA);
		when(consultaRepository.findById(8L)).thenReturn(Optional.of(existente));
		when(cadastroBasicoService.salvar(any(), any())).thenAnswer(inv -> inv.getArgument(1));

		var res = consultaService.marcarRealizada(8L);
		assertEquals(StatusConsulta.REALIZADA, res.getStatus());
	}

	@Test
	void atualizar_deveFalhar_quandoConflitoIgnorandoPropriaConsulta() {
		var atual = new Consulta(); atual.setId(9L);
		var prof = prof(); atual.setProfissional(prof);
		var novoHorario = LocalDateTime.now().plusDays(3);

		var outra = new Consulta(); outra.setId(10L); outra.setProfissional(prof); outra.setDataHora(novoHorario); outra.setStatus(StatusConsulta.AGENDADA);

		when(consultaRepository.findById(9L)).thenReturn(Optional.of(atual));
		when(consultaRepository.findByProfissionalId(prof.getId())).thenReturn(List.of(outra));

		var dados = new Consulta(); dados.setDataHora(novoHorario);

		assertThrows(IllegalArgumentException.class, () -> consultaService.atualizar(9L, dados));
	}
}
