package com.globalsolution.wellbeing_api.domain.model.enums;

/**
 * Representa o status atual da consulta.
 */
public enum StatusConsulta {
    AGENDADA, // Consulta marcada, mas ainda não ocorreu
    REALIZADA, // Consulta que foi realizada com sucesso
    CANCELADA // Consulta que foi cancelada pelo paciente ou profissional
}
