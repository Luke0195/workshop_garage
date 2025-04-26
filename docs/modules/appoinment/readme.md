# Agendamento de Serviços

## Agendar Serviço

> ### Dados de Entrada
* **user_id** (required) – ID do usuário associado ao agendamento (exemplo: "123").
* **vehicle_id** (required) – ID do veículo associado ao agendamento (exemplo: "456").
* **service_type** (required) – Tipo de serviço a ser agendado (exemplo: "Troca de óleo").
* **appointment_date** (required, ISO 8601) – Data e horário do agendamento (exemplo: "2024-02-01T10:00:00Z").
* **status** (optional) – Status do agendamento (exemplo: "Confirmado").

> ### Casos de Sucesso
* [X] Recebe uma requisição do tipo **POST** na rota **/api/appointment**.
* [X] Valida que os campos obrigatórios (**user_id**, **vehicle_id**, **service_type**, **appointment_date**) estão presentes.
* [X] Cria o agendamento de serviço quando todos os dados são válidos.
* [X] Retorna **201 Created** com os dados do agendamento criado.

> ### Exceções
* [X] Retorna **404 Not Found** se o endpoint **/api/appointment** não for encontrado.
* [X] Retorna **400 Bad Request** se algum dos campos obrigatórios não for informado.
* [X] Retorna **500 Internal Server Error** caso ocorra um erro ao salvar o agendamento.

---
