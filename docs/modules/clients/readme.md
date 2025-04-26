# Gestão de clientes

## Cadastrar Cliente

> ### Dados de Entrada
* **name** (required) – Nome completo do cliente (exemplo: "João Silva").
* **email** (required) – Endereço de e-mail (exemplo: "joao.silva@email.com").
* **phone** (optional) – Número de telefone (exemplo: "(11) 98765-4321").
* **cpf** (required, valid) – CPF do cliente (exemplo: "123.456.789-00").
* **zipcode** (optional) – CEP do cliente (exemplo: "12345-678").
* **address** (optional) – Endereço completo (exemplo: "Rua X").
* **number** (optional) – Número da residência (exemplo: "123").
* **complement** (optional) – Complemento do endereço (exemplo: "Apartamento 45").
* **status** – Status do cliente (exemplo: "Ativo").
* **createdAt** – Data de criação do cadastro (formato ISO 8601, ex: "2025-04-20T12:00:00Z").

> ### Casos de Sucesso
* [X] Recebe uma requisição do tipo **POST** na rota **/api/client**.
* [X] Valida que os campos obrigatórios (**name**, **email**, **cpf**) estão presentes.
* [X] Não permite que um cliente seja cadastrado com o mesmo **e-mail**.
* [X] Não permite que um cliente seja cadastrado com o **mesmo CPF**.
* [X] Adiciona um cliente quando todos os dados são válidos.
* [X] Retorna **201 Created** com os dados do cliente criado em caso de sucesso.

> ### Exceções
* [X] Retorna erro **404 Not Found** se o endpoint **/api/clients** não for encontrado.
* [X] Retorna erro **400 Bad Request** se o **nome** não for informado.
* [X] Retorna erro **400 Bad Request** se o **email** não for informado.
* [X] Retorna erro **400 Bad Request** se o **email** informado for inválido.
* [X] Retorna erro **400 Bad Request** se o **cpf** não for informado.
* [X] Retorna erro **422 Unprocessable Entity** se o **email** informado já estiver sendo utilizado.
* [X] Retorna erro **422 Unprocessable Entity** se o **CPF** informado já estiver sendo utilizado.
* [X] Retorna erro **500 Internal Server Error** caso ocorra um erro ao salvar o cliente.

---

## Listar clientes

> ### Casos de Sucesso
* [X] Recebe uma requisição do tipo **GET** na rota **/clients**.
* [X] Retorna a lista de clientes cadastrados.
* [X] Retorna **200 OK** com a lista de clientes.

> ### Exceções
* [X] Retorna **404 Not Found** se o endpoint **/clients** não for encontrado.
* [X] Retorna **500 Internal Server Error** caso ocorra um erro ao carregar os clientes.

---

## Atualizar cliente

> ### Casos de Sucesso
* [X] Recebe uma requisição do tipo **PUT** na rota **/clients/{id}**.
* [X] Valida se o **id** que está sendo passado na rota é válido.
* [X] Valida se o **id** informado existe.
* [X] Atualiza os dados do cliente caso o **id** seja válido.
* [X] Retorna **200 OK** com os dados atualizados.

> ### Exceções
* [X] Retorna **404 Not Found** se o endpoint **/clients/{id}** não for encontrado.
* [X] Retorna **404 Not Found** caso o **id** do cliente não for encontrado.
* [X] Retorna **500 Internal Server Error** caso ocorra um erro ao atualizar o cliente.

---

## Deletar cliente

> ### Casos de Sucesso
* [X] Recebe uma requisição do tipo **DELETE** na rota **/clients/{id}**.
* [X] Valida se o **id** que está sendo passado na rota é válido.
* [X] Valida se o **id** informado existe.
* [X] Deleta a informação do cliente.
* [X] Retorna **204 No Content** em caso de sucesso.

> ### Exceções
* [X] Retorna **404 Not Found** se o endpoint **/clients/{id}** não for encontrado.
* [X] Retorna **404 Not Found** caso o **id** do cliente não for encontrado.
* [X] Retorna **500 Internal Server Error** caso ocorra um erro ao deletar o cliente.

---

## Listar clientes por Período

> ### Dados de Entrada
* **startDate** (obrigatório, formato ISO: yyyy-MM-dd) – Data de início do período (exemplo: "2024-01-01").
* **endDate** (obrigatório, formato ISO: yyyy-MM-dd) – Data de fim do período (exemplo: "2024-01-31").

> ### Casos de Sucesso
* [X] Recebe uma requisição do tipo **GET** na rota **/users?startDate=2024-01-01&endDate=2024-01-31**.
* [X] Filtra os clientes com base no campo **createdAt** entre as datas informadas.
* [X] Retorna **200 OK** com a lista dos clientes cadastrados nesse período.

> ### Exceções
* [X] Retorna **400 Bad Request** se os parâmetros **startDate** ou **endDate** não forem informados.
* [X] Retorna **400 Bad Request** se as **datas** estiverem em formato inválido.
* [X] Retorna **400 Bad Request** se a **data de início** for posterior à **data de fim**.
* [X] Retorna **500 Internal Server Error** caso ocorra um erro ao processar a consulta.
