# Gestão de Veículos

## Cadastrar Veículo

> ### Dados de Entrada
* **name** (required) – Nome do veículo (exemplo: "Fusca").
* **brand** (required) – Marca do veículo (exemplo: "Volkswagen").
* **model** (required) – Modelo do veículo (exemplo: "Fusca 1970").
* **year** (required, valid) – Ano de fabricação do veículo (exemplo: "1970").
* **plate** (required, valid) – Placa do veículo (exemplo: "ABC-1234").
* **owner_id** (required) – ID do proprietário do veículo (exemplo: "123").

> ### Casos de Sucesso
* [X] Recebe uma requisição do tipo **POST** na rota **/api/vehicle**.
* [X] Valida que os campos obrigatórios (**name**, **brand**, **model**, **year**, **plate**, **owner_id**) estão presentes.
* [X] Não permite que um veículo seja cadastrado com a mesma **placa**.
* [X] Adiciona o veículo quando todos os dados são válidos.
* [X] Retorna **201 Created** com os dados do veículo criado em caso de sucesso.

> ### Exceções
* [X] Retorna erro **404 Not Found** se o endpoint **/api/vehicle** não for encontrado.
* [X] Retorna erro **400 Bad Request** se o **name** não for informado.
* [X] Retorna erro **400 Bad Request** se o **brand** não for informado.
* [X] Retorna erro **400 Bad Request** se o **model** não for informado.
* [X] Retorna erro **400 Bad Request** se o **year** não for informado.
* [X] Retorna erro **400 Bad Request** se o **plate** não for informado.
* [X] Retorna erro **422 Unprocessable Entity** se a **placa** informada já estiver sendo utilizada.
* [X] Retorna erro **500 Internal Server Error** caso ocorra um erro ao salvar o veículo.

---

## Listar Veículos

> ### Casos de Sucesso
* [X] Recebe uma requisição do tipo **GET** na rota **/vehicles**.
* [X] Retorna a lista de veículos cadastrados.
* [X] Retorna **200 OK** com a lista de veículos.

> ### Exceções
* [X] Retorna **404 Not Found** se o endpoint **/vehicles** não for encontrado.
* [X] Retorna **500 Internal Server Error** caso ocorra um erro ao carregar os veículos.

---

## Detalhar Veículo

> ### Dados de Entrada
* **id** (required) – ID único do veículo (exemplo: "123").

> ### Casos de Sucesso
* [X] Recebe uma requisição do tipo **GET** na rota **/vehicles/{id}**.
* [X] Retorna os detalhes do veículo baseado no **id** informado.
* [X] Retorna **200 OK** com as informações detalhadas do veículo.

> ### Exceções
* [X] Retorna **404 Not Found** se o endpoint **/vehicles/{id}** não for encontrado.
* [X] Retorna **404 Not Found** caso o **id** do veículo não for encontrado.
* [X] Retorna **500 Internal Server Error** caso ocorra um erro ao buscar as informações do veículo.

---

## Atualizar Veículo

> ### Dados de Entrada
* **name** (optional) – Nome do veículo (exemplo: "Fusca").
* **brand** (optional) – Marca do veículo (exemplo: "Volkswagen").
* **model** (optional) – Modelo do veículo (exemplo: "Fusca 1970").
* **year** (optional) – Ano de fabricação do veículo (exemplo: "1970").
* **plate** (optional) – Placa do veículo (exemplo: "ABC-1234").

> ### Casos de Sucesso
* [X] Recebe uma requisição do tipo **PUT** na rota **/vehicles/{id}**.
* [X] Valida se o **id** que está sendo passado na rota é válido.
* [X] Valida se o **id** informado existe.
* [X] Atualiza os dados do veículo caso o **id** seja válido.
* [X] Retorna **200 OK** com os dados atualizados.

> ### Exceções
* [X] Retorna **404 Not Found** se o endpoint **/vehicles/{id}** não for encontrado.
* [X] Retorna **404 Not Found** caso o **id** do veículo não for encontrado.
* [X] Retorna erro **422 Unprocessable Entity** se a **placa** informada já estiver sendo utilizada.
* [X] Retorna **500 Internal Server Error** caso ocorra um erro ao atualizar o veículo.

---

## Deletar Veículo

> ### Casos de Sucesso
* [X] Recebe uma requisição do tipo **DELETE** na rota **/vehicles/{id}**.
* [X] Valida se o **id** que está sendo passado na rota é válido.
* [X] Valida se o **id** informado existe.
* [X] Deleta a informação do veículo.
* [X] Retorna **204 No Content** em caso de sucesso.

> ### Exceções
* [X] Retorna **404 Not Found** se o endpoint **/vehicles/{id}** não for encontrado.
* [X] Retorna **404 Not Found** caso o **id** do veículo não for encontrado.
* [X] Retorna **500 Internal Server Error** caso ocorra um erro ao deletar o veículo.

---

## Filtrar Veículos

> ### Dados de Entrada
* **startDate** (optional, formato ISO: yyyy-MM-dd) – Data de início do período de filtro (exemplo: "2024-01-01").
* **endDate** (optional, formato ISO: yyyy-MM-dd) – Data de fim do período de filtro (exemplo: "2024-01-31").
* **name** (optional) – Nome do veículo a ser filtrado (exemplo: "Fusca").
* **brand** (optional) – Marca do veículo a ser filtrado (exemplo: "Volkswagen").

> ### Casos de Sucesso
* [X] Recebe uma requisição do tipo **GET** na rota **/vehicles?startDate=2024-01-01&endDate=2024-01-31&name=Fusca**.
* [X] Filtra os veículos com base nos parâmetros informados.
* [X] Retorna **200 OK** com a lista de veículos filtrados.

> ### Exceções
* [X] Retorna **400 Bad Request** se os parâmetros **startDate** ou **endDate** não forem informados em formato válido.
* [X] Retorna **500 Internal Server Error** caso ocorra um erro ao processar o filtro.
