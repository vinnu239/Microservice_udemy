**What is springcloud?**

It is providing project for developing microservices
Microservices offering by springcloud:

**springcloudOpen Feign** : Spring Cloud OpenFeign is a declarative HTTP client library for building RESTful microservices. It integrates seamlessly with Spring Cloud and simplifies the development of HTTP clients by allowing you to create interfaces that resemble the API of the target service.

**springcloudNetflix Eureka**:Spring Cloud Eureka, part of the Spring Cloud Netflix project, is a service registry that allows microservices to register themselves and discover other services.
In Simple terms it act as a mediator for urls where we use id or name which we registered with eureka in all other microservices irrespective of the original URL.

**SpringCloud LoadBalancer** : it will balance the requests, A load balancer distributes user traffic across multiple instances of your applications.

**Spring cloud API Gateway** :It is a lightweight, reactive API gateway built on top of the Spring framework.
In simple terms it is mediator or entry point of req between consumer and microservices and also it helps to implement the common functionality like authentication and help to log the req and res of all the micro services.
Internally it use pre and post filters
pre: req --> authenticate --> to mS
post: MS res --> log/give response --> consumer
It internally performs the load balancer as well so we dont need to declare seperate load balancer if we use api-gateway

**Fault Tolerance**:  Fault tolerance helps applications fail fast and recover smoothly by guiding how and when certain requests occur and by providing fallback strategies to handle common errors.

**Sleuth and Zipkin** : Helpful for Tracing and monitoring the requests.
By using Sleuth we can trace the req and also known how our microservices are communicating.
Zipkin : It is the Ui tool to see the complete flow of requests.

**config server**: it will helpful to manage the common properties across the microservices.
it will helpful to store it like in git repo.

**Why we need to create response class(DTO) extra if we already have entity class?**


Overall, using DTOs instead of exposing entities directly enhances data encapsulation, security, performance, flexibility, and maintainability in Java applications, especially when dealing with communication between different layers or external systems.

**Why is spring web flux and use of it?**

Spring WebFlux is a reactive web framework that is part of the Spring Framework 5.0 and above. It allows developers to build asynchronous, non-blocking, and event-driven web applications, taking advantage of reactive programming principles.

Normally suppose we have student project and address project. We need to access address details from the student project. In spring 5 below we will use rest template to make this happen. But after spring 5 and above it is depricated so for that one we are using web flux

**Why we need open feign if we already have webclient to call the api(restful services)?**

Open feign will be useful to build the api's ina a declarative way. Just like how we call annotations. Its make our life easy and loosely coupled.


