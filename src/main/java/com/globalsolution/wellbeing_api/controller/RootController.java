package com.globalsolution.wellbeing_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Endpoint raiz ("/") para checagem rápida de status.
 * Retorna HTTP 200 com um payload simples indicando que a API está ativa.
 */
@RestController
public class RootController {

    /**
     * Retorna um pequeno JSON com status e timestamp.
     */
    @GetMapping("/")
    public Map<String, Object> root() {
        Map<String, Object> body = new HashMap<>();
        body.put("status", "ok");
        body.put("service", "wellbeing-api");
        body.put("timestamp", Instant.now().toString());
        return body;
    }
}
