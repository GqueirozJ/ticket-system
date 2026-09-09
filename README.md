# 🎟️ Ticket System - Sistema de Venda de Ingressos Resiliente

Aplicação desenvolvida para simular a venda de ingressos em cenários de alta concorrência, utilizando Spring Boot 3, PostgreSQL e Redis (Redisson) para garantir concorrência thread-safe por meio de Trava Distribuída (Distributed Lock).

---

## 🚀 Problema Resolvido

Em sistemas de bilheteria com alta demanda, múltiplos usuários tentam comprar o mesmo assentoingresso exatamente no mesmo milissegundo (Race Condition). Sem o tratamento adequado de concorrência, o sistema pode incorrer em overselling (vender o mesmo ingresso para pessoas diferentes).

Para resolver isso, este projeto utiliza o Redisson para aplicar um bloqueio distribuído no Redis no nível do ingresso (`ticketId`). Apenas uma threadinstância consegue adquirir a trava por vez, processar a transação no PostgreSQL e atualizar o status do ingresso.

---

## 🛠️ Tecnologias Utilizadas

- Java 21
- Spring Boot 3.2.4
- Spring Data JPA  Hibernate
- PostgreSQL (Persistência relacional)
- Redis & Redisson (Distributed Lock e Cache)
- Lombok
- Docker  Docker Compose

---

## 📂 Arquitetura do Projeto

O projeto segue os princípios de Domain-Driven Design (DDD), separado em camadas bem definidas

```text
com.ticket.system
├── application        # Casos de uso (Services) e DTOs de entradasaída
├── domain             # Modelo de domínio (Entities) e Repositórios
└── infrastructure     # Controllers REST e Configurações (Redisson, Initializer)