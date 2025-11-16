# 🌿 Wellbeing API


![./img/header.png](./img/header.png)

---

![Java](https://img.shields.io/badge/Java-17+-blue) ![Spring
Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-brightgreen)
![Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento-yellow)
![License](https://img.shields.io/badge/License-MIT-purple)

------------------------------------------------------------------------

## ✨ Sumário

-   [Grupo](#-grupo)
-   [Visão Geral do Projeto](#-visão-geral-do-projeto)
-   [Como Executar](#-como-executar)
-   [Arquitetura e Estrutura do
    Código](#-arquitetura-e-estrutura-do-código)
-   [Dependências](#-dependências)
-   [Endpoints da API](#-endpoints-da-api)
-   [Postman](#-postman)
-   [Segurança](#-segurança)
-   [Seções Avançadas](#-seções-avançadas)
    -   [Modelagem ER](#modelagem-er)
    -   [Decisões de Arquitetura](#decisões-de-arquitetura)
    -   [Futuras Expansões](#futuras-expansões)
-   [Links Úteis](#-links-úteis)

------------------------------------------------------------------------

## 👥 Grupo

- **Alice Santos Bulhões:** RM554499
- **Eduardo Oliveira Cardoso Madid:** RM556349

------------------------------------------------------------------------

## 🔎 Visão Geral do Projeto

A **Wellbeing API** é uma plataforma REST construída em **Java 17** com **Spring Boot 3.5.7**, focada em promover **saúde mental** e **bem-estar** 🌱💚, totalmente alinhada ao **ODS 3: Saúde e Bem-Estar** da ONU.
Ela funciona como o coração de um ecossistema voltado ao acompanhamento emocional, oferecendo recursos modernos, seguros e acessíveis para ajudar pessoas a cuidarem melhor de sua saúde mental.

### 💡 O que a API oferece?

A aplicação disponibiliza um conjunto completo de funcionalidades:

* 👤 **Gerenciamento de Pacientes**
* 📓 **Registros Diários** (humor, sono, ansiedade)
* 🩺 **Profissionais de Saúde e Especialidades**
* 📅 **Consultas** (agendamento, atualização, cancelamento)
* ❤️ **Condições de Saúde Mental do Paciente**
* 📚 **Recursos de Apoio** (artigos, vídeos, práticas)

Tudo isso pensado para criar uma experiência integrada de apoio emocional.

### 🤝 Parceria com o Adeptus

A API faz parte do ecossistema do **Adeptus**, plataforma de requalificação profissional.
Essa união garante aos usuários um ambiente que cuida não só da **evolução profissional**, mas também do **bem-estar mental durante toda a jornada** 🌟🧘‍♂️.

### 🧩 Estrutura Forte e Flexível

A base técnica do projeto inclui:

* 🛠 **Arquitetura em camadas**
* 🧪 **Validações com Bean Validation**
* 🚨 **Tratamento centralizado de erros**
* 🌱 **Seeds automáticos de dados**
* 🗄️ **Banco H2 no dev e MySQL no prod**
* 📘 **Documentação via Swagger**
* 🔁 **DTO + Mappers para isolamento do domínio**

### 🚀 Pronto para evoluir

A arquitetura já foi pensada para suportar futuras melhorias, como:

* 🔐 Autenticação JWT
* 📊 Dashboards e indicadores emocionais
* 🔔 Sistema de lembretes e notificações
* 📈 Análises avançadas de bem-estar

------------------------------------------------------------------------

## 🚀 Como Executar

``` bash
./mvnw test               # Rodar testes
./mvnw spring-boot:run    # Rodar API (H2)
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod  # Rodar API (MySQL)
```

------------------------------------------------------------------------

## 🧱 Arquitetura e Estrutura do Código

    src/main/java/com/globalsolution/wellbeing_api
    ├── controller/      # Endpoints REST
    ├── service/         # Regras de negócio
    ├── repository/      # Persistência
    ├── domain/          # Models, DTOs, Exceptions
    └── mapper/          # Conversões Entity ↔ DTO

------------------------------------------------------------------------

## 📦 Dependências

-   Spring Web
-   Spring Data JPA
-   Spring Validation
-   Spring Security
-   SpringDoc OpenAPI
-   H2 (dev)
-   MySQL connector (prod)
-   Lombok

------------------------------------------------------------------------

## 🔍 Endpoints da API

### 👤 Pacientes

-   `GET /pacientes`
-   `POST /pacientes`
-   `PUT /pacientes/{id}`
-   `DELETE /pacientes/{id}`

### 📓 Registros Diários

-   `GET /pacientes/{id}/registros`
-   `POST /pacientes/{id}/registros`

### 👩‍⚕️ Profissionais

-   `GET /profissionais`
-   `POST /profissionais`

### 🧠 Especialidades

-   `GET /especialidades`
-   `POST /especialidades`

### 📅 Consultas

-   `GET /consultas`
-   `POST /consultas`

### ❤️ Condições do Paciente

-   `GET /pacientes/{id}/condicoes`
-   `POST /pacientes/{id}/condicoes`

### 📚 Recursos de Apoio

-   `GET /recursos`
-   `POST /recursos`

------------------------------------------------------------------------

## 🧪 Postman

Coleção disponível em:
`postman/wellbeing-api.postman_collection.json`

------------------------------------------------------------------------

## 🔐 Segurança

-   🔓 Dev: Acesso liberado
-   🔒 Prod: JWT recomendado, CORS restrito e perfis configuráveis

------------------------------------------------------------------------

## 🧬 Seções Avançadas

### Modelagem ER

    Paciente 1---N Condição
    Paciente 1---N Registro Diário
    Paciente 1---N Consulta N---1 Profissional
    Profissional N---N Especialidade
    Profissional 1---N Recurso de Apoio

### Decisões de Arquitetura

-   DTOs para isolamento do domínio
-   Services para centralizar regras
-   Mappers para remover duplicação
-   PERFIS: `dev` (H2) e `prod` (MySQL)

### Futuras Expansões

-   Autenticação completa (JWT)
-   Sistema de indicadores emocionais
-   Dashboard com analytics
-   Notificações e lembretes

------------------------------------------------------------------------

## 🔗 Links Úteis

-   📘 Swagger UI
    `http://localhost:8080/swagger-ui/index.html`

-   🗄️ H2 Console
    `http://localhost:8080/h2-console`

-   🖥️ Github
    `https://github.com/L-A-N-E/Wellbeing_API/`





