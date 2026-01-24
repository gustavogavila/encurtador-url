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

