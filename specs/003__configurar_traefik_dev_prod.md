## Objetivo
Configurar o Traefik para ambientes de desenvolvimento e produção.

## Pacote
- Raiz do projeto

## Referências
- docker-compose.dev.yml
- docker-compose.yml

## Explicações adicionais
- O Traefik deve ser configurado para rotear o tráfego corretamente entre os serviços em ambos os ambientes.
- Utilize certificados SSL para o ambiente de produção.
- Certifique-se de que o Traefik esteja configurado para lidar com redirecionamentos HTTP para HTTPS no ambiente de produção.
- Certifique-se de que o Traefik tenha acesso ao Docker socket para monitorar os containers.
- No ambiente de desenvolvimento, o Traefik deve permitir acesso sem SSL para facilitar os testes.
- As configurações específicas para cada ambiente devem ser mantidas em arquivos separados (docker-compose.dev.yml para desenvolvimento e docker-compose.yml para produção).
- Assegure-se de que as regras de roteamento estejam corretamente definidas para cada serviço.
- Certifique-se de seguir padrões de segurança adequados, especialmente no ambiente de produção.
- Certifique-se de não expor segredos ou informações sensíveis nos arquivos de configuração.

## Direcionamento para o Claude Code
- Realize a implementação seguindo o direcionamento do CLAUDE.md
- Se necessário, atualize os arquivos CLAUDE.md e README.md com novas instruções.
- Não escreva nenhum teste agora.