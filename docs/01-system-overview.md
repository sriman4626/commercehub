\## Request Flow



1\. Client sends request.

2\. API Gateway receives the request.

3\. Gateway validates the JWT token.

4\. Request is routed to the correct microservice.

5\. Microservice processes the request.

6\. Database is updated.

7\. Events are published to Kafka if required.

8\. Other services consume the event.

9\. Response returns through API Gateway.

