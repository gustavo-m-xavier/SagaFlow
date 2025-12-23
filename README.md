# SagaFlow

## Visão Geral

Em arquiteturas de microsserviços, não é possível utilizar transações ACID tradicionais para garantir consistência entre múltiplos sistemas. Cada serviço possui sua própria base de dados, sua própria transação local e seu próprio ciclo de vida.

O SagaFlow é um laboratório prático que demonstra como lidar com esse desafio utilizando o Saga Pattern (Orquestrado), combinando comunicação síncrona, mensageria e compensações lógicas para garantir consistência eventual e resiliência a falhas.

O projeto simula um fluxo real de negócio envolvendo:

- Criação de pedidos
- Processamento de pagamento
- Reserva de estoque

Cada etapa é executada como uma transação local independente, coordenada por um serviço orquestrador, que controla o estado da saga e dispara ações ou compensações conforme o resultado de cada passo.

## Funcionamento da Saga (Visão Técnica)

O SagaFlow utiliza o Saga Pattern Orquestrado, onde um serviço central controla o fluxo da transação distribuída.

### Serviços envolvidos

- `Order Service`
  Responsável por criar e gerenciar pedidos.

- `Payment Service`
  Simula o processamento de pagamentos.

- `Inventory Service`
  Responsável por reservar e liberar estoque.

- `Orchestrator Service`
  Coordena o fluxo da saga, mantendo o estado da transação distribuída.

### Fluxo simplificado da Saga

1. Cliente cria um pedido

   - Requisição REST para o `Order Service`
   - Pedido é criado com status `CREATED`

2. Orchestrator inicia a saga

   - Recebe o evento de pedido criado
   - Inicia o fluxo da saga

3. Processamento de pagamento

   - Orchestrator envia comando para o `Payment Service`
   - Em caso de sucesso → segue o fluxo
   - Em caso de falha → inicia compensação

4. Reserva de estoque

   - Orchestrator solicita reserva ao `Inventory Service`
   - Em caso de sucesso → saga finalizada com sucesso
   - Em caso de falha → compensação de pagamento

5. Finalização

   - Pedido é marcado como `COMPLETED` ou `CANCELLED`
   - Consistência eventual garantida

### Compensações

Caso qualquer etapa falhe:

- Pagamento aprovado → estoque falhou
  → Pagamento é compensado
- Pedido criado → pagamento falhou
  → Pedido é cancelado

Cada compensação é uma ação explícita, não um rollback automático de banco de dados.

## Stack

### Backend

- Java 17
- Spring Boot 3
- Spring Web
- Spring Data JPA
- Spring AMQP (RabbitMQ)

### Persistência

- PostgreSQL (um banco por serviço)
- Flyway para versionamento de schema

### Comunicação

- REST (comandos síncronos)
- RabbitMQ (eventos assíncronos)

### Observabilidade

- Spring Boot Actuator
- Micrometer
- Prometheus + Grafana (planejado)

### Infraestrutura

- Docker
- Docker Compose

## Estrutura do Projeto

```text
sagaflow/
├── order-service/
├── payment-service/
├── inventory-service/
├── orchestrator-service/
├── docker-compose.yml
└── README.md
```

Cada diretório representa um microsserviço Spring Boot independente, com:

- `Dockerfile`
- Banco de dados próprio
- Configurações isoladas

## Como Rodar Localmente

### Pré-requisitos

- Docker
- Docker Compose
- Java 17 (opcional, apenas se quiser rodar sem Docker)

### Subindo o ambiente completo

Na raiz do projeto:

```bash
docker-compose up --build
```

Isso irá subir:

- 4 microsserviços Spring Boot
- PostgreSQL
- (RabbitMQ quando habilitado)

### Portas padrão

| Serviço              | Porta |
| -------------------- | ----- |
| Inventory Service    | 8081  |
| Orchestrator Service | 8082  |
| Order Service        | 8083  |
| Payment Service      | 8084  |
| PostgreSQL           | 5432  |

Exemplo de acesso:

```text
http://localhost:8083/orders
```

### Build manual (opcional)

Caso queira buildar manualmente algum serviço:

```bash
cd order-service
./mvnw clean package -DskipTests
```

## Próximos Passos Planejados

- Implementar mensageria com RabbitMQ
- Persistência do estado da saga
- Retry e dead letter queues
- Observabilidade com Prometheus e Grafana
- Testes de falha e cenários de rollback
- Idempotência de eventos

## Objetivo Educacional

Este projeto tem como foco:

- Estudo prático de transações distribuídas
- Aplicação real do Saga Pattern
- Arquitetura de microsserviços
- Comunicação síncrona vs assíncrona
- Resiliência e consistência eventual
