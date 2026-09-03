# 🛒 CommerceHub

CommerceHub is a backend e-commerce platform built using a **microservices architecture** with Java and Spring Boot.

The project is designed to simulate a real-world e-commerce backend consisting of independent services for authentication, users, products, inventory, and orders.

The project focuses on:

- Microservices architecture
- Service discovery
- API Gateway
- JWT authentication and authorization
- REST APIs
- Inter-service communication
- Inventory management
- Optimistic locking
- Order processing
- Distributed transaction and consistency challenges

---

# 🏗️ Architecture

```text
                         ┌─────────────────┐
                         │     Client      │
                         └────────┬────────┘
                                  │
                                  ▼
                         ┌─────────────────┐
                         │   API Gateway   │
                         │  Spring Cloud   │
                         └────────┬────────┘
                                  │
              ┌───────────────────┼────────────────────┐
              │                   │                    │
              ▼                   ▼                    ▼
       ┌─────────────┐    ┌─────────────┐     ┌─────────────┐
       │    Auth     │    │    User     │     │   Product   │
       │   Service   │    │   Service   │     │   Service   │
       └─────────────┘    └─────────────┘     └──────┬──────┘
                                                      │
                                                      │
                                                      ▼
                                             ┌─────────────────┐
                                             │    Inventory    │
                                             │     Service     │
                                             └─────────────────┘

                                  ┌─────────────────┐
                                  │      Order      │
                                  │     Service     │
                                  └────────┬────────┘
                                           │
                              ┌────────────┴────────────┐
                              │                         │
                              ▼                         ▼
                       Product Service          Inventory Service
                       (OpenFeign)              (OpenFeign)
