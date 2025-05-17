> ### Recuperação de Senha

> #### Dados de Entrada
 * **email**(required)

> ### Casos de Sucesso
- [ ] Recebe uma requisição do tipo **POST** na rota /api/forgotpassword.
- [ ] Valida o campo de entrada(email).
- [ ] Verifica se o email informado é um email válido.
- [ ] Retorna o usuário que possui o email selecionado.
- [ ] Gera um email com dos dados para recuperação de senha.
- [ ] Retorna 204 em caso de sucesso.


> ### Casos Exceções
* [ ] Retorna erro **404 Not Found** se o endpoint **/api/resetpassword** não for encontrado.
* [ ] Retorna **400** se o email inválido for fornecido.
* [ ] Retorna **422** se o email não for encontrado.
* [ ] Retornar erro 500 se ocorrer um erro ao enviar o email.
