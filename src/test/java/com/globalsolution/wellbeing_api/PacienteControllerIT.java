package com.globalsolution.wellbeing_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globalsolution.wellbeing_api.domain.dto.paciente.PacienteRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PacienteControllerIT {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    private String toJson(Object o) throws Exception {
	return objectMapper.writeValueAsString(o);
    }

    @Test
    void fluxoCompleto_CRUD_Paciente() throws Exception {
	// CREATE
	PacienteRequestDTO dto = new PacienteRequestDTO();
	dto.setNome("Paciente Teste");
	dto.setCpf("111.222.333-44");
	dto.setEmail("paciente.teste@wellbeing.test");

	ResultActions create = mockMvc.perform(post("/pacientes")
		.contentType(MediaType.APPLICATION_JSON)
		.content(toJson(dto)));

	create.andExpect(status().isCreated())
	      .andExpect(jsonPath("$.id", notNullValue()))
	      .andExpect(jsonPath("$.nome", is("Paciente Teste")));

	Long id = objectMapper.readTree(create.andReturn().getResponse().getContentAsString()).get("id").asLong();

	// READ by id
	mockMvc.perform(get("/pacientes/" + id))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id", is(id.intValue())))
		.andExpect(jsonPath("$.cpf", is("111.222.333-44")));

	// LIST
	mockMvc.perform(get("/pacientes"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", not(empty())));

	// UPDATE
	dto.setNome("Paciente Atualizado");
	mockMvc.perform(put("/pacientes/" + id)
		.contentType(MediaType.APPLICATION_JSON)
		.content(toJson(dto)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.nome", is("Paciente Atualizado")));

	// DELETE
	mockMvc.perform(delete("/pacientes/" + id))
		.andExpect(status().isNoContent());

	// GET after delete -> 404
	mockMvc.perform(get("/pacientes/" + id))
		.andExpect(status().isNotFound());
    }

    @Test
    void deveFalhar_quandoCpfDuplicado() throws Exception {
	PacienteRequestDTO dto1 = new PacienteRequestDTO();
	dto1.setNome("Duplicado 1");
	dto1.setCpf("222.333.444-55");

	PacienteRequestDTO dto2 = new PacienteRequestDTO();
	dto2.setNome("Duplicado 2");
	dto2.setCpf("222.333.444-55");

	mockMvc.perform(post("/pacientes").contentType(MediaType.APPLICATION_JSON).content(toJson(dto1)))
		.andExpect(status().isCreated());

	mockMvc.perform(post("/pacientes").contentType(MediaType.APPLICATION_JSON).content(toJson(dto2)))
		.andExpect(status().isBadRequest());
    }
}
