&#x20;   Client

&#x20;                            |

&#x20;                      API Gateway

&#x20;                            |

&#x20;         ---------------------------------------

&#x20;         |                                     |

&#x20;    Authentication                        Routing

&#x20;         |

&#x20;    Auth Service

&#x20;         |

&#x20;    JWT Validation

&#x20;         |

\---------------------------------------------------------------

|          |          |          |          |                  |

User    Product   Inventory    Cart      Order             Review

&#x20;                                 |

&#x20;                            Payment

&#x20;                                 |

&#x20;                            Shipping

&#x20;                                 |

&#x20;                          Notification



&#x20;               Kafka Event Bus

\---------------------------------------------------------------

|                     |                     |

Analytics      Recommendation      Notification



\---------------------------------------------------------------

Each Service

&#x20;     |

Own Database

&#x20; 

\--------------------------------------------------------------





\## Infrastructure Components



| Component | Purpose |

|-----------|---------|

| API Gateway | Single entry point |

| Eureka Server | Service Discovery |

| Config Server | Centralized Configuration |

| Kafka | Event Streaming |

| MySQL | Transactional Data |

| MongoDB | Recommendation Data |

| PostgreSQL | Analytics |

| Redis | Cache |

| Zipkin | Distributed Tracing |

| Prometheus | Metrics Collection |

| Grafana | Monitoring Dashboard |





\--------------------------------------

\## Decide Communication Style:

| Scenario                     | Technology | Why?                                    |

| ---------------------------- | ---------- | --------------------------------------- |

| Client → Gateway             | REST       | Standard HTTP API                       |

| Gateway → Auth               | REST       | External request routing                |

| Order → Inventory            | OpenFeign  | Simple synchronous request              |

| Order → Payment              | OpenFeign  | Immediate response needed               |

| Order Created → Notification | Kafka      | Doesn't block order creation            |

| Order Created → Analytics    | Kafka      | Event-driven processing                 |

| Inventory Lookup             | gRPC       | Low latency and efficient serialization |







\------------------------------------------------------

\## Tech Stack



| Layer         | Technology                         |

| ------------- | ---------------------------------- |

| Java          | Java 21 (or Java 17 if you prefer) |

| Framework     | Spring Boot 3                      |

| Build Tool    | Maven                              |

| Cloud         | Spring Cloud                       |

| Registry      | Eureka                             |

| Gateway       | Spring Cloud Gateway               |

| Config        | Spring Cloud Config                |

| Security      | Spring Security + JWT + OAuth2     |

| Database      | MySQL                              |

| Cache         | Redis                              |

| Messaging     | Kafka                              |

| RPC           | gRPC                               |

| Tracing       | OpenTelemetry + Zipkin             |

| Metrics       | Prometheus                         |

| Dashboard     | Grafana                            |

| Logging       | ELK/OpenSearch                     |

| Containers    | Docker                             |

| Orchestration | Kubernetes                         |

| CI/CD         | GitHub Actions + Jenkins           |





