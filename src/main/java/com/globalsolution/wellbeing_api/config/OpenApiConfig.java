package com.globalsolution.wellbeing_api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Classe responsável por configurar a documentação Swagger/OpenAPI
 * para todo o projeto WellBeing.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Configuração principal do OpenAPI, usada pelo Swagger UI.
     * Define informações gerais da API e organiza tags para cada controller.
     */
    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("WellBeing - Plataforma de Apoio Emocional")
                        .description("API RESTful desenvolvida para gerenciamento de pacientes, registros diários, consultas, " +
                                     "profissionais de saúde e recursos de apoio emocional. Projeto FIAP Global Solution 2025.")
                        .version("1.0.0")
                        .license(new License()
                                .name("Apache License 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
}
