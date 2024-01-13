# Custumer Service

### Requisitos

1. JDK 11
2. Maven 3

### Comandos Prova Prática

**Caixas preenchidas onde os requisitos foram preenchidos para validação.**

Acesse o swagger em: http://localhost:8080/swagger-ui/

- [x] Clone o projeto: `https://github.com/filipemaulerm/segurosunimed-test.git`
- [x] Execute a aplicação.
- [x] Acesse: `http://localhost:8080/customers`
- [x] Neste ponto será retornado a lista de clientes pré-cadastrada.
- [x] Faça filtros de cliente nas buscas por nome, email e genero.
- [x] Adicione endpoints para criar um novo cliente, editar um cliente e excluir um cliente.
- [x] Valide os dados antes de cadastrar ou editar.
- [x] Pagine a listagem de clientes.
- [x] Possibilite o cadastro de múltiplos endereços para um cliente.
- [x] No cadastro de endereço permita inserir apenas o CEP carregando os dados via consumo do
  serviço: https://viacep.com.br/
- [x] Faça filtros de clientes nas buscas agora para os campos cidade e estado
- [x] Envie a url do seu repositório no github para análise.

Obs.: Será um diferencial implementações como: tratamento de exceções (RestControllerAdvice), testes, validações, uso de mecanismos modernos da linguagem, frontend, autenticação e documentação. 

- [x] Tratamento de exceções
- [x] Testes (unidade e integração)
- [x] Validações
- [x] Documentação
- [ ] Frontend
- [ ] Autenticação