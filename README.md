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
- Traefik v3.2
- Docker & Docker Compose
- Gradle 8.11

## Como Executar

### Desenvolvimento Local (apenas PostgreSQL)
```bash
# Iniciar PostgreSQL em container
docker-compose -f docker-compose.dev.yml up -d

# Executar aplicação localmente
./gradlew bootRun

# Parar PostgreSQL
docker-compose -f docker-compose.dev.yml down
```

A aplicação estará disponível em: http://localhost:8080

### Ambiente de Produção (com Docker)
```bash
# 1. Configurar variáveis de ambiente (opcional)
cp .env.example .env
# Edite .env conforme necessário

# 2. Iniciar todos os serviços (Traefik + App + PostgreSQL)
docker-compose up -d

# 3. Verificar logs
docker-compose logs -f app

# 4. Parar todos os serviços
docker-compose down
```

**Serviços disponíveis:**
- Aplicação: http://encurtador.localhost
- Traefik Dashboard: http://traefik.localhost:8080

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
docker-compose.yml       # Produção (Traefik + App + DB)
docker-compose.dev.yml   # Desenvolvimento (apenas DB)
```

## Referências
Para mais detalhes sobre a arquitetura, comandos e configurações, consulte o arquivo [CLAUDE.md](CLAUDE.md).

