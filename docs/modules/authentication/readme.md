> ### Autenticação 

> ####  Dados de Entrada

  * **email** (required)
  * **password** (required)

> #### Casos de Sucesso 

* [ ] Recebe uma requisição do tipo **POST** na rota /api/signin
* [ ] Válidos os campos obrigatórios(email, password).
* [ ] Verifica se o email do usuário é um e-mail válido.
* [ ] Verifica se a senha informado do usuário faz match com a senha criptograda.
* [ ] Gera o um token de autenticação caso os dados sejam válidos.
* [ ] Retorna um status 200 com o token e a data de expiração do token.


> #### Casos de Exceção

> #### Exceções
* [X] Retorna erro **404 Not Found** se o endpoint **/api/signin** não for encontrado.
* [ ] Retorna erro **401** quando o email do usuário não for encontrado.
* [ ] Retorna erro **401** quando a senha não fizer match com a senha criptografada.
* [ ] Retorna erro 500 se ocorre um erro ao gerar o token.
---

> ### Recuperação de Senha

> #### Dados de Entrada
* **email**(required)

> #### Casos de Sucesso
- [ ] Recebe uma requisição do tipo **POST** na rota /api/forgotpassword.
- [ ] Valida o campo de entrada(email).
- [ ] Verifica se o email informado é um email válido.
- [ ] Retorna o usuário que possui o email selecionado.
- [ ] Gera um email com dos dados para recuperação de senha.
- [ ] Retorna 204 em caso de sucesso.


> #### Casos Exceções
* [ ] Retorna erro **404 Not Found** se o endpoint **/api/forgotpassword** não for encontrado.
* [ ] Retorna **400** se o email inválido for fornecido.
* [ ] Retorna **422** se o email não for encontrado.
* [ ] Retornar erro 500 se ocorrer um erro ao enviar o email.

---

### Atualizar Senha

> ### Dados de Entrada

* **password**(required)
* **token**(required)

> #### Casos de Sucesso
* [ ] Recebe um requisição do tipo **PATCH** se o endpoint **/api/resetpassword?token=any_token** não for encontrado.
* [ ] Recebe valida se o token fornecido na url é um token válido.
* [ ] Valida se o campo senha é valido.
* [ ] Atualiza senha do usuário.
* [ ] Retorna 200 em caso de succeso.

> #### Casos de Exceções
* [ ] Retorna erro **404 Not Found** se o endpoint **/api/resetpassword?token=any_token** não for encontrado.
* [ ] Retorna **400** se o token não informado.
* [ ] Retornar **422** se o token não for encontrado. 
* [ ] Retornar erro 500 se ocorrer um erro ao atualizar a senha.
