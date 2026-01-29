## Objetivo
Criar um endpoint POST /api/shorten que receba uma URL longa e retorne uma URL curta e a URL longa original.

## Pacote
O arquivo UrlShortenerController deve ser criado no pacote `src/main/java/com/gusta/encurtador_url/controllers`.

## Referências
- Classe UrlShortenerController
- Classe UrlShortenerService

## Explicações adicionais
- O endpoint deve aceitar requisições no formato JSON com o seguinte corpo:
  ```json
  {
    "url": "https://exemplo.com/minha-url"
  }
  ```
- A resposta deve ser no formato JSON com o seguinte corpo:
  ```json
  {
    "short_url": "https://short.local/abc123",
    "original_url": "https://exemplo.com/minha-url"
  }
  ```
- A URL curta deve ser gerada com a base "https://short.local/" seguida de um código único de 6 caracteres alfanuméricos.
- Utilize a classe UrlShortenerService para gerar a URL curta.

## Direcionamento para o Claude Code
- Realize a implementação seguindo o direcionamento do CLAUDE.md
- Se necessário, atualize o arquivo CLAUDE.md com novas instruções.
- Não escreva nenhum teste agora.