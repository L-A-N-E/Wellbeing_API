package com.globalsolution.wellbeing_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globalsolution.wellbeing_api.domain.dto.consulta.ConsultaRequestDTO;
import com.globalsolution.wellbeing_api.domain.model.Paciente;
import com.globalsolution.wellbeing_api.domain.model.ProfissionalSaude;
import com.globalsolution.wellbeing_api.repository.PacienteRepository;
import com.globalsolution.wellbeing_api.repository.ProfissionalSaudeRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ConsultaControllerIT {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @Autowired PacienteRepository pacienteRepository;
    @Autowired ProfissionalSaudeRepository profissionalRepository;

    private Paciente paciente;
    private ProfissionalSaude profissional;

    @BeforeEach
    void setup() {
	paciente = pacienteRepository.findAll().stream().findFirst().orElse(null);
	profissional = profissionalRepository.findAll().stream().findFirst().orElse(null);
    }

    private String toJson(Object o) throws Exception { return objectMapper.writeValueAsString(o); }

    @Test
    void deveCadastrarEAtualizarCancelarRealizarConsulta() throws Exception {
	// POST /consultas
	ConsultaRequestDTO req = new ConsultaRequestDTO();
	req.setDataHora(LocalDateTime.now().plusDays(1).withSecond(0).withNano(0));
	req.setObservacao("Avaliação inicial");
	req.setPacienteId(paciente.getId());
	req.setProfissionalId(profissional.getId());

	ResultActions create = mockMvc.perform(post("/consultas")
		.contentType(MediaType.APPLICATION_JSON)
		.content(toJson(req)));

	create.andExpect(status().isCreated())
	      .andExpect(jsonPath("$.id", notNullValue()))
	      .andExpect(jsonPath("$.status", is("AGENDADA")));

	Long id = objectMapper.readTree(create.andReturn().getResponse().getContentAsString()).get("id").asLong();

	// PUT /consultas/{id}
	req.setObservacao("Atualizada");
	req.setDataHora(LocalDateTime.now().plusDays(2).withSecond(0).withNano(0));
	mockMvc.perform(put("/consultas/" + id)
		.contentType(MediaType.APPLICATION_JSON)
		.content(toJson(req)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.observacao", is("Atualizada")));

	// POST /consultas/{id}/cancelar
	mockMvc.perform(post("/consultas/" + id + "/cancelar"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.status", is("CANCELADA")));

	// POST /consultas/{id}/realizar (pode reativar cenário)
	// Cria outra consulta para realizar
	ConsultaRequestDTO req2 = new ConsultaRequestDTO();
	req2.setDataHora(LocalDateTime.now().plusDays(3).withSecond(0).withNano(0));
	req2.setPacienteId(paciente.getId());
	req2.setProfissionalId(profissional.getId());
	Long id2 = objectMapper.readTree(mockMvc.perform(post("/consultas")
		.contentType(MediaType.APPLICATION_JSON)
		.content(toJson(req2)))
		.andExpect(status().isCreated())
		.andReturn().getResponse().getContentAsString()).get("id").asLong();

	mockMvc.perform(post("/consultas/" + id2 + "/realizar"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.status", is("REALIZADA")));
    }
}
