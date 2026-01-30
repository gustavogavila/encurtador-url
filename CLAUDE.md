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
- Traefik v2.11 (LTS) como reverse proxy
- Docker Compose com PostgreSQL
- Suporte a imagens nativas GraalVM

## Comandos de Build

### Build Local
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

# Compilar imagem Docker usando buildpacks
./gradlew bootBuildImage
```

### Build com Docker
```bash
# Construir imagem Docker usando o Dockerfile
docker build -t encurtador-url:latest .

# Construir via Docker Compose (também inicia os serviços)
docker-compose build

# Rebuild e restart de um serviço específico
docker-compose up -d --build app
```

## Docker Compose

O projeto possui dois arquivos Docker Compose para diferentes ambientes:

### Desenvolvimento (docker-compose.dev.yml)
Ambiente completo para desenvolvimento local (Traefik + App + DB):
```bash
# Construir a imagem da aplicação
docker build -t encurtador-url:dev .

# Iniciar todos os serviços de desenvolvimento
docker-compose -f docker-compose.dev.yml up -d

# Visualizar logs
docker-compose -f docker-compose.dev.yml logs -f app

# Rebuild da aplicação
docker-compose -f docker-compose.dev.yml up -d --build app

# Parar serviços
docker-compose -f docker-compose.dev.yml down

# Parar e remover volumes
docker-compose -f docker-compose.dev.yml down -v
```

**Serviços disponíveis em desenvolvimento:**
- **Aplicação**: http://encurtador.localhost
- **Traefik Dashboard**: http://traefik.localhost:8080
- **PostgreSQL**: localhost:5432

**Características do ambiente de desenvolvimento:**
- Sem SSL/HTTPS (apenas HTTP na porta 80)
- Dashboard do Traefik sem autenticação
- Logs em nível DEBUG para facilitar troubleshooting
- Banco de dados exposto na porta 5432 para acesso direto
- Domínios .localhost para facilitar testes locais

### Produção (docker-compose.yml)
Ambiente completo com Traefik, SSL/HTTPS automático, PostgreSQL e aplicação:
```bash
# Copiar arquivo de exemplo de variáveis de ambiente
cp .env.example .env

# Editar .env com suas configurações (IMPORTANTE!)
# - ACME_EMAIL: seu email para Let's Encrypt
# - APP_DOMAIN: domínio da aplicação
# - TRAEFIK_DOMAIN: domínio do dashboard do Traefik
# - TRAEFIK_DASHBOARD_AUTH: credenciais de acesso ao dashboard
# - Credenciais do PostgreSQL
nano .env

# Construir a imagem da aplicação
docker build -t encurtador-url:latest .

# Iniciar todos os serviços
docker-compose up -d

# Visualizar logs
docker-compose logs -f app

# Parar serviços
docker-compose down
```

**Serviços disponíveis em produção:**
- **Aplicação**: https://${APP_DOMAIN} (HTTPS automático com Let's Encrypt)
- **Traefik Dashboard**: https://${TRAEFIK_DOMAIN} (com autenticação básica)

**Características do ambiente de produção:**
- SSL/HTTPS automático via Let's Encrypt
- Redirecionamento automático HTTP → HTTPS
- Certificados SSL renovados automaticamente
- Dashboard do Traefik protegido com autenticação básica
- Headers de segurança (HSTS, etc.)
- Banco de dados isolado na rede interna
- Logs em nível INFO

**Gerando senha para o dashboard do Traefik:**
```bash
# Instalar htpasswd (se necessário)
sudo apt-get install apache2-utils

# Gerar hash da senha (substitua 'usuario' e 'senha')
echo $(htpasswd -nb usuario senha) | sed -e s/\\$/\\$\\$/g

# Copiar o output para a variável TRAEFIK_DASHBOARD_AUTH no .env
```

## Banco de Dados

A aplicação utiliza:
- **PostgreSQL** em produção (configurado via docker-compose.yml)
- **H2** para desenvolvimento/testes
- **Flyway** para migrações de schema (migrações em `src/main/resources/db/migration/`)

Configuração padrão do banco de dados:
- Database: mydatabase
- User: myuser
- Password: secret
- Port: 5432 (dev) / interno (produção)

## Estrutura do Projeto

```
src/main/java/com/gusta/encurtador_url/  # Código principal da aplicação
src/main/resources/
  ├── application.yaml                    # Configuração do Spring Boot
  └── db/migration/                       # Scripts de migração Flyway
src/test/java/                            # Código de testes
Dockerfile                                # Imagem Docker multi-stage (build + runtime)
docker-compose.yml                        # Configuração produção (Traefik + App + DB)
docker-compose.dev.yml                    # Configuração desenvolvimento (Traefik + App + DB)
.dockerignore                             # Arquivos excluídos do contexto Docker
.env.example                              # Exemplo de variáveis de ambiente
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

### Docker

- **Dockerfile multi-stage**: Build otimizado com Gradle + runtime Alpine JRE 21
- **Segurança**: Aplicação roda com usuário não-root
- **JVM otimizada**: Configurada para containers com G1GC e uso de 75% da RAM disponível
- **Health checks**: Configurados para PostgreSQL e aplicação
- **Volumes persistentes**: Dados do PostgreSQL e certificados do Traefik são persistidos

### Traefik (Reverse Proxy)

**Desenvolvimento:**
- HTTP apenas (porta 80)
- Dashboard acessível sem autenticação em http://traefik.localhost:8080
- Logs em DEBUG para facilitar troubleshooting
- Acesso ao Docker socket em modo read-only
- Roteamento baseado em Host (*.localhost)

**Produção:**
- HTTPS automático com Let's Encrypt (porta 443)
- Redirecionamento automático HTTP → HTTPS
- Certificados SSL gerenciados automaticamente (ACME HTTP Challenge)
- Dashboard protegido com autenticação básica
- Headers de segurança (HSTS com preload)
- Renovação automática de certificados
- Volumes persistentes para certificados SSL em `/certificates/acme.json`

**Configuração via Labels:**
O Traefik é configurado via labels do Docker nos serviços:
- `traefik.enable=true`: Habilita o roteamento para o serviço
- `traefik.http.routers.<nome>.rule`: Define a regra de roteamento (ex: Host)
- `traefik.http.routers.<nome>.entrypoints`: Define entrada (web=HTTP, websecure=HTTPS)
- `traefik.http.routers.<nome>.tls.certresolver`: Define resolver de certificado (letsencrypt)
- `traefik.http.services.<nome>.loadbalancer.server.port`: Porta interna do container

**Segurança:**
- Docker socket montado em modo read-only
- Exposição de serviços desabilitada por padrão (exposedbydefault=false)
- Dashboard com autenticação em produção
- Certificados armazenados em volume persistente
- Headers de segurança aplicados automaticamente
