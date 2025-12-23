# SagaFlow

## Visão Geral

Em arquiteturas de microsserviços, não é possível utilizar transações ACID tradicionais para garantir consistência entre múltiplos sistemas e bancos de dados. Cada serviço possui sua própria base de dados e seu próprio ciclo de vida transacional.

O SagaFlow surge como um laboratório prático para demonstrar como lidar com esse desafio utilizando o Saga Pattern (Orquestrado), mensageria e compensações lógicas, garantindo consistência eventual e resiliência em cenários de falha.

O projeto simula um fluxo real de negócio envolvendo:

- Criação de pedidos
- Processamento de pagamento
- Reserva de estoque

Cada etapa é executada como uma transação local independente, coordenada por um serviço orquestrador.

## Objetivos do Projeto

Demonstrar o funcionamento de transações distribuídas
Aplicar Saga Pattern na prática
Trabalhar com comunicação síncrona e assíncrona
Implementar compensações em caso de falha
Exercitar observabilidade e resiliência
Criar um projeto de referência para estudos e portfólio

## Stack

### Backend

- Java 17
- Spring Boot 3
- Spring Web
- Spring Data JPA
- Spring AMQP (RabbitMQ)

### Persistência

- PostgreSQL (um banco por serviço)
- Flyway

### Comunicação

- REST (comandos)
- RabbitMQ (eventos)

### Observabilidade

- Spring Actuator
- Micrometer
- Prometheus + Grafana (fase posterior)

### Infraestrutura

- Docker
- Docker Compose
