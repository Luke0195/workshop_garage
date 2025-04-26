# Gestão de Ordem de Serviço

## Cadastrar Ordem de Serviço

> ### Dados de Entrada
* **user_id** (required) – ID do usuário (cliente) associado à ordem de serviço (exemplo: "123").
* **vehicle_id** (required) – ID do veículo associado à ordem de serviço (exemplo: "456").
* **description** (required) – Descrição do serviço realizado (exemplo: "Troca de óleo e filtro").
* **value** (required) – Valor do serviço realizado (exemplo: "200.00").
* **entry_date** (required, ISO 8601) – Data de entrada do veículo (exemplo: "2024-01-01T09:00:00Z").
* **exit_date** (required, ISO 8601) – Data de saída do veículo (exemplo: "2024-01-01T17:00:00Z").
* **payment_method** (required) – Forma de pagamento (exemplo: "Cartão", "Dinheiro", "Pix").
* **advance** (optional) – Valor do adiantamento (exemplo: "50.00").

> ### Casos de Sucesso
* [X] Recebe uma requisição do tipo **POST** na rota **/api/service-order**.
* [X] Valida que os campos obrigatórios (**user_id**, **vehicle_id**, **description**, **value**, **entry_date**, **exit_date**, **payment_method**) estão presentes.
* [X] Cria a ordem de serviço quando todos os dados são válidos.
* [X] Retorna **201 Created** com os dados da ordem de serviço criada.

> ### Exceções
* [X] Retorna erro **404 Not Found** se o endpoint **/api/service-order** não for encontrado.
* [X] Retorna erro **400 Bad Request** se algum dos campos obrigatórios não for informado.
* [X] Retorna erro **500 Internal Server Error** caso ocorra um erro ao salvar a ordem de serviço.

---

## Listar Ordens de Serviço

> ### Casos de Sucesso
* [X] Recebe uma requisição do tipo **GET** na rota **/service-orders**.
* [X] Retorna a lista de todas as ordens de serviço cadastradas.
* [X] Retorna **200 OK** com a lista de ordens de serviço.

> ### Exceções
* [X] Retorna **404 Not Found** se o endpoint **/service-orders** não for encontrado.
* [X] Retorna **500 Internal Server Error** caso ocorra um erro ao carregar as ordens de serviço.

---

## Detalhar Ordem de Serviço

> ### Dados de Entrada
* **id** (required) – ID único da ordem de serviço (exemplo: "123").

> ### Casos de Sucesso
* [X] Recebe uma requisição do tipo **GET** na rota **/service-orders/{id}**.
* [X] Retorna os detalhes da ordem de serviço com base no **id** informado.
* [X] Retorna **200 OK** com as informações detalhadas da ordem de serviço.

> ### Exceções
* [X] Retorna **404 Not Found** se o endpoint **/service-orders/{id}** não for encontrado.
* [X] Retorna **404 Not Found** caso o **id** da ordem de serviço não for encontrado.
* [X] Retorna **500 Internal Server Error** caso ocorra um erro ao buscar as informações da ordem de serviço.

---

## Atualizar Ordem de Serviço

> ### Dados de Entrada
* **description** (optional) – Descrição do serviço realizado (exemplo: "Troca de óleo e filtro").
* **value** (optional) – Valor do serviço realizado (exemplo: "200.00").
* **entry_date** (optional, ISO 8601) – Data de entrada do veículo (exemplo: "2024-01-01T09:00:00Z").
* **exit_date** (optional, ISO 8601) – Data de saída do veículo (exemplo: "2024-01-01T17:00:00Z").
* **payment_method** (optional) – Forma de pagamento (exemplo: "Cartão", "Dinheiro", "Pix").
* **advance** (optional) – Valor do adiantamento (exemplo: "50.00").

> ### Casos de Sucesso
* [X] Recebe uma requisição do tipo **PUT** na rota **/service-orders/{id}**.
* [X] Valida se o **id** da ordem de serviço é válido.
* [X] Atualiza os dados da ordem de serviço com os dados fornecidos.
* [X] Retorna **200 OK** com os dados atualizados da ordem de serviço.

> ### Exceções
* [X] Retorna **404 Not Found** se o endpoint **/service-orders/{id}** não for encontrado.
* [X] Retorna **404 Not Found** caso o **id** da ordem de serviço não for encontrado.
* [X] Retorna **500 Internal Server Error** caso ocorra um erro ao atualizar a ordem de serviço.

---

## Deletar Ordem de Serviço

> ### Casos de Sucesso
* [X] Recebe uma requisição do tipo **DELETE** na rota **/service-orders/{id}**.
* [X] Valida se o **id** da ordem de serviço é válido.
* [X] Deleta a ordem de serviço.
* [X] Retorna **204 No Content** em caso de sucesso.

> ### Exceções
* [X] Retorna **404 Not Found** se o endpoint **/service-orders/{id}** não for encontrado.
* [X] Retorna **404 Not Found** caso o **id** da ordem de serviço não for encontrado.
* [X] Retorna **500 Internal Server Error** caso ocorra um erro ao deletar a ordem de serviço.

---

## Integração com Meio de Pagamento

> ### Casos de Sucesso
* [X] O sistema deve integrar-se com uma API de pagamento para realizar o pagamento da ordem de serviço.
* [X] Após o pagamento ser processado, o sistema deve atualizar o status da ordem de serviço.
* [X] O sistema deve retornar **200 OK** quando o pagamento for aprovado.

> ### Exceções
* [X] Retorna **400 Bad Request** se houver algum erro na integração com o meio de pagamento.
* [X] Retorna **500 Internal Server Error** caso ocorra um erro ao processar o pagamento.

---

## Gerar Comprovante em PDF

> ### Casos de Sucesso
* [X] O sistema deve gerar um comprovante em PDF após a conclusão da ordem de serviço.
* [X] O PDF deve conter informações detalhadas da ordem de serviço, como dados do usuário, veículo, valor, descrição, data de entrada e saída.
* [X] O sistema deve disponibilizar o PDF para download ou envio por e-mail.

> ### Exceções
* [X] Retorna **500 Internal Server Error** caso ocorra um erro ao gerar o PDF.

---