# Gestão Financeira

## Registrar Pagamento e Recebimento

> ### Dados de Entrada
* **value** (required) – Valor do pagamento ou recebimento (exemplo: "200.00").
* **payment_method** (required) – Forma de pagamento (exemplo: "Pix", "Cartão", "Dinheiro").
* **payment_date** (required, ISO 8601) – Data do pagamento ou recebimento (exemplo: "2024-01-01T09:00:00Z").
* **description** (optional) – Descrição do pagamento ou recebimento (exemplo: "Pagamento de ordem de serviço").

> ### Casos de Sucesso
* [X] Recebe uma requisição do tipo **POST** na rota **/api/payment**.
* [X] Registra o pagamento ou recebimento quando todos os dados são válidos.
* [X] Retorna **201 Created** com os dados do pagamento ou recebimento registrado.

> ### Exceções
* [X] Retorna **404 Not Found** se o endpoint **/api/payment** não for encontrado.
* [X] Retorna **400 Bad Request** se algum dos campos obrigatórios não for informado.
* [X] Retorna **500 Internal Server Error** caso ocorra um erro ao salvar o pagamento.

---