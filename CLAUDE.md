# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Visão Geral do Projeto

Esta é uma aplicação de encurtador de URLs construída com Spring Boot 4.0 e Java 21. A aplicação identifica e encurta URLs automaticamente com base no domínio e estrutura da URL, inclui gerenciamento de links, redirecionamento dinâmico e rastreamento de métricas. Utiliza Traefik como reverse proxy e Docker para deployment.

**Tecnologias Principais:**
- Spring Boot 4.0.2 (Web MVC, Data JDBC, Validation)
- Java 21
- PostgreSQL (produção) / H2 (desenvolvimento)
- Flyway para migrações de banco de dados
- Lombok para redução de código boilerplate
- Docker Compose com PostgreSQL
- Suporte a imagens nativas GraalVM

## Comandos de Build

```bash
# Compilar o projeto
./gradlew build

# Executar a aplicação (com Docker Compose para PostgreSQL)
./gradlew bootRun

# Executar testes
./gradlew test

# Limpar build
./gradlew clean

# Compilar imagem nativa
./gradlew nativeCompile

# Executar imagem nativa
./gradlew nativeRun

# Compilar imagem Docker
./gradlew bootBuildImage
```

## Banco de Dados

A aplicação utiliza:
- **PostgreSQL** em produção (configurado via compose.yaml)
- **H2** para desenvolvimento/testes
- **Flyway** para migrações de schema (migrações em `src/main/resources/db/migration/`)

Configuração do banco de dados no compose.yaml:
- Database: mydatabase
- User: myuser
- Password: secret
- Port: 5432

## Estrutura do Projeto

```
src/main/java/com/gusta/encurtador_url/  # Código principal da aplicação
src/main/resources/
  ├── application.yaml                    # Configuração do Spring Boot
  └── db/migration/                       # Scripts de migração Flyway
src/test/java/                            # Código de testes
```

## Arquitetura

A aplicação segue uma arquitetura Spring Boot padrão com:
- **Redirecionamento baseado em código curto**: URLs são redirecionadas baseadas apenas no short_code presente no caminho da URL
- **Roteamento dinâmico**: Traefik atua como reverse proxy para roteamento dinâmico de requisições
- **Deploy com Docker**: Ambiente completo configurado com Docker Compose
- **Persistência de banco de dados**: Spring Data JDBC para acesso a dados com migrações Flyway

O ponto de entrada principal é `EncurtadorUrlApplication.java` com auto-configuração padrão do Spring Boot.

## Notas de Desenvolvimento

- O projeto usa anotações Lombok para reduzir código boilerplate
- Suporte ao Docker Compose do Spring Boot está habilitado para desenvolvimento (inicia PostgreSQL automaticamente ao executar bootRun)
- Suporte a imagens nativas GraalVM está configurado para deployment otimizado em performance
- Validação de beans está habilitada via spring-boot-starter-validation
