> ### Autenticação 

> ###  Dados de Entrada

  * **email** (required)
  * **password** (required)

> ### Casos de Sucesso 

* [ ] Recebe uma requisição do tipo **POST** na rota /api/signin
* [ ] Válidos os campos obrigatórios(email, password).
* [ ] Verifica se o email do usuário é um e-mail válido.
* [ ] Verifica se a senha informado do usuário faz match com a senha criptograda.
* [ ] Gera o um token de autenticação caso os dados sejam válidos.
* [ ] Retorna um status 200 com o token e a data de expiração do token.


> ### Casos de Exceção

> ### Exceções
* [X] Retorna erro **404 Not Found** se o endpoint **/api/signin** não for encontrado.
* [ ] Retorna erro **401** quando o email do usuário não for encontrado.
* [ ] Retorna erro **401** quando a senha não fizer match com a senha criptografada.
* [ ] Retorna erro 500 se ocorre um erro ao gerar o token.