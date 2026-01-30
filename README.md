# encurtador-url
Encurtador de URLs

## Descrição geral
- App para encurtar links e identificar URLs encurtadas automaticamente com base no domínio e estrutura da URL.
- Inclui funcionalidades de gerenciamento de links, redirecionamento dinâmico e registro de métricas.
- Utiliza Docker e Traefik como reverse proxy.

## Objetivo
- Criar uma app que permita encurtar links e gerenciá-los.
- Permitir que links encurtados sejam redirecionados com base apenas no short_code presente na URL.
- Utilizar Traefik como reverse proxy para roteamento dinâmico.
- Configurar todo o ambiente com Docker Compose para facilitar o deploy.

## Tecnologias
- Java 21
- Spring Boot 4.0.2
- PostgreSQL 16
- Traefik v2.11 (LTS)
- Docker & Docker Compose
- Gradle 9.2

## Como Executar

### Opção 1: Desenvolvimento Local (Spring Boot + PostgreSQL no Docker)
```bash
# Iniciar apenas PostgreSQL em container
docker-compose -f docker-compose.dev.yml up -d postgres

# Executar aplicação localmente via Gradle
./gradlew bootRun

# Parar PostgreSQL
docker-compose -f docker-compose.dev.yml down
```

A aplicação estará disponível em: http://localhost:8080

### Opção 2: Desenvolvimento Completo (Traefik + App + DB)
```bash
# Construir imagem da aplicação
docker build -t encurtador-url:dev .

# Iniciar todos os serviços (Traefik + App + PostgreSQL)
docker-compose -f docker-compose.dev.yml up -d

# Verificar logs
docker-compose -f docker-compose.dev.yml logs -f app

# Parar todos os serviços
docker-compose -f docker-compose.dev.yml down
```

**Serviços disponíveis:**
- Aplicação: http://encurtador.localhost
- Traefik Dashboard: http://traefik.localhost:8080
- PostgreSQL: localhost:5432

### Ambiente de Produção (com SSL/HTTPS automático)
```bash
# 1. Configurar variáveis de ambiente (OBRIGATÓRIO)
cp .env.example .env

# 2. Editar .env com suas configurações
# - ACME_EMAIL: seu email para Let's Encrypt
# - APP_DOMAIN: domínio da aplicação (ex: api.meusite.com)
# - TRAEFIK_DOMAIN: domínio do dashboard (ex: traefik.meusite.com)
# - TRAEFIK_DASHBOARD_AUTH: credenciais do dashboard
# - Credenciais do PostgreSQL
nano .env

# 3. Construir imagem da aplicação
docker build -t encurtador-url:latest .

# 4. Iniciar todos os serviços (Traefik + App + PostgreSQL)
docker-compose up -d

# 5. Verificar logs
docker-compose logs -f app

# 6. Parar todos os serviços
docker-compose down
```

**Serviços disponíveis em produção:**
- Aplicação: https://${APP_DOMAIN} (HTTPS automático com Let's Encrypt)
- Traefik Dashboard: https://${TRAEFIK_DOMAIN} (com autenticação básica)

**Nota:** Em produção, o Traefik configura automaticamente certificados SSL via Let's Encrypt e redireciona todo tráfego HTTP para HTTPS.

### Build Manual com Docker
```bash
# Construir imagem
docker build -t encurtador-url:latest .

# Ou via Docker Compose
docker-compose build
```

## Estrutura do Projeto
```
src/main/java/           # Código fonte Java
src/main/resources/      # Recursos e configurações
  └── db/migration/      # Migrações Flyway
Dockerfile               # Imagem Docker multi-stage
docker-compose.yml       # Produção (Traefik + App + DB + SSL)
docker-compose.dev.yml   # Desenvolvimento (Traefik + App + DB)
.env.example             # Exemplo de variáveis de ambiente
```

## Recursos de Segurança

- **SSL/HTTPS Automático:** Let's Encrypt gerencia certificados em produção
- **Redirecionamento HTTP → HTTPS:** Automático em produção
- **Dashboard Protegido:** Autenticação básica para o Traefik dashboard
- **Headers de Segurança:** HSTS com preload habilitado
- **Docker Socket Read-Only:** Traefik acessa Docker em modo somente leitura
- **Usuário Não-Root:** Aplicação executa com usuário não privilegiado

## Referências
Para mais detalhes sobre a arquitetura, comandos e configurações, consulte o arquivo [CLAUDE.md](CLAUDE.md).

