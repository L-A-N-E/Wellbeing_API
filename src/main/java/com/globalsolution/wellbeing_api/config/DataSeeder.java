package com.globalsolution.wellbeing_api.config;

import com.globalsolution.wellbeing_api.domain.model.*;
import com.globalsolution.wellbeing_api.domain.model.enums.StatusConsulta;
import com.globalsolution.wellbeing_api.domain.model.enums.TipoRecurso;
import com.globalsolution.wellbeing_api.repository.*;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Insere dados iniciais para facilitar a avaliação/local run.
 * Executa de forma idempotente: só cria quando não há registros.
 */
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final PacienteRepository pacienteRepository;
    private final EspecialidadeRepository especialidadeRepository;
    private final ProfissionalSaudeRepository profissionalRepository;
    private final RegistroDiarioRepository registroRepository;
    private final ConsultaRepository consultaRepository;
    private final RecursoApoioRepository recursoRepository;

    @Override
    public void run(String... args) {
        if (pacienteRepository.count() > 0 || profissionalRepository.count() > 0) {
            return; // já populado
        }

        // Especialidades
        Especialidade psicologia = new Especialidade();
        psicologia.setNome("Psicologia Clínica");
        psicologia.setDescricao("Atendimento clínico psicológico");
        Especialidade psiquiatria = new Especialidade();
        psiquiatria.setNome("Psiquiatria");
        psiquiatria.setDescricao("Diagnóstico e tratamento medicamentoso");
        especialidadeRepository.saveAll(List.of(psicologia, psiquiatria));

        // Profissionais
        ProfissionalSaude prof1 = new ProfissionalSaude();
        prof1.setNome("Dra. Ana Saúde");
        prof1.setEmail("ana.saude@wellbeing.test");
        prof1.setRegistroProfissional("CRP12345");
        prof1.setEspecialidades(List.of(psicologia));

        ProfissionalSaude prof2 = new ProfissionalSaude();
        prof2.setNome("Dr. Bruno Psiq");
        prof2.setEmail("bruno.psiq@wellbeing.test");
        prof2.setRegistroProfissional("CRM54321");
        prof2.setEspecialidades(List.of(psiquiatria));
        profissionalRepository.saveAll(List.of(prof1, prof2));

        // Pacientes
        Paciente pac1 = new Paciente();
        pac1.setNome("Maria Paciente");
        pac1.setCpf("123.456.789-00");
        pac1.setEmail("maria@wellbeing.test");
        pac1.setDataNascimento(LocalDate.of(1990, 5, 15));

        Paciente pac2 = new Paciente();
        pac2.setNome("João Calmo");
        pac2.setCpf("987.654.321-00");
        pac2.setEmail("joao@wellbeing.test");
        pac2.setDataNascimento(LocalDate.of(1985, 8, 20));
        pacienteRepository.saveAll(List.of(pac1, pac2));

        // Registros diários
        RegistroDiario r1 = new RegistroDiario();
        r1.setHumor(7);
        r1.setEmocao("calmo");
        r1.setObservacao("Dia produtivo");
        r1.setDataHora(LocalDateTime.now().minusDays(1));
        r1.setPaciente(pac1);

        RegistroDiario r2 = new RegistroDiario();
        r2.setHumor(4);
        r2.setEmocao("ansioso");
        r2.setObservacao("Noite mal dormida");
        r2.setDataHora(LocalDateTime.now());
        r2.setPaciente(pac1);
        registroRepository.saveAll(List.of(r1, r2));

        // Consultas
        Consulta c1 = new Consulta();
        c1.setPaciente(pac1);
        c1.setProfissional(prof1);
        c1.setDataHora(LocalDateTime.now().plusDays(2));
        c1.setStatus(StatusConsulta.AGENDADA);
        c1.setObservacao("Primeira avaliação");

        Consulta c2 = new Consulta();
        c2.setPaciente(pac2);
        c2.setProfissional(prof2);
        c2.setDataHora(LocalDateTime.now().plusDays(3));
        c2.setStatus(StatusConsulta.AGENDADA);
        c2.setObservacao("Retorno");
        consultaRepository.saveAll(List.of(c1, c2));

        // Recursos de apoio
        RecursoApoio rec1 = new RecursoApoio();
        rec1.setTitulo("Técnicas de Respiração");
        rec1.setDescricao("Guia prático para reduzir ansiedade");
        rec1.setUrl("https://example.com/respiracao");
        rec1.setTipo(TipoRecurso.ARTIGO);
        rec1.setProfissional(prof1);

        RecursoApoio rec2 = new RecursoApoio();
        rec2.setTitulo("Sono e Saúde Mental");
        rec2.setDescricao("Podcast sobre hábitos de sono");
        rec2.setUrl("https://example.com/sono");
        rec2.setTipo(TipoRecurso.PODCAST);
        rec2.setProfissional(prof2);
        recursoRepository.saveAll(List.of(rec1, rec2));
    }
}
