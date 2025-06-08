> ### Autenticação 

> ####  Dados de Entrada

  * **email** (required)
  * **password** (required)

> #### Casos de Sucesso 

* [X] Recebe uma requisição do tipo **POST** na rota /api/signin
* [X] Válidos os campos obrigatórios(email, password).
* [X] Verifica se o email do usuário é um e-mail válido.
* [X] Verifica se a senha informado do usuário faz match com a senha criptograda.
* [X] Gera o um token de autenticação caso os dados sejam válidos.
* [X] Retorna um status 200 com o token e a data de expiração do token.


> #### Casos de Exceção

> #### Exceções
* [X] Retorna erro **404 Not Found** se o endpoint **/api/signin** não for encontrado.
* [X] Retorna erro **401** quando o email do usuário não for encontrado.
* [X] Retorna erro **401** quando a senha não fizer match com a senha criptografada.
* [X] Retorna erro 500 se ocorre um erro ao gerar o token.
---

> ### Recuperação de Senha

> #### Dados de Entrada
* **email**(required)

> #### Casos de Sucesso
- [X] Recebe uma requisição do tipo **POST** na rota /api/forgotpassword.
- [X] Valida o campo de entrada(email).
- [X] Verifica se o email informado é um email válido.
- [X] Retorna o usuário que possui o email selecionado.
- [X] Gera um email com dos dados para recuperação de senha.
- [X] Retorna 204 em caso de sucesso.


> #### Casos Exceções
* [X] Retorna erro **404 Not Found** se o endpoint **/api/forgotpassword** não for encontrado.
* [X] Retorna **400** se o email inválido for fornecido.
* [X] Retorna **422** se o email não for encontrado.
* [X] Retornar erro 500 se ocorrer um erro ao enviar o email.

---

### Atualizar Senha

> ### Dados de Entrada

* **password**(required)
* **token**(required)

> #### Casos de Sucesso
* [X] Recebe um requisição do tipo **PATCH** se o endpoint **/api/resetpassword?token=any_token** não for encontrado.
* [X] Recebe valida se o token fornecido na url é um token válido.
* [X] Valida se o campo senha é valido.
* [X] Atualiza senha do usuário.
* [X] Retorna 200 em caso de succeso.

> #### Casos de Exceções
* [X] Retorna erro **404 Not Found** se o endpoint **/api/resetpassword?token=any_token** não for encontrado.
* [X] Retorna **400** se o token não informado.
* [X] Retornar **422** se o token não for encontrado. 
* [X] Retornar erro 500 se ocorrer um erro ao atualizar a senha.
