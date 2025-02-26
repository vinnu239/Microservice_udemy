**Course -Name**

Learn OpenFeign REST Client, Spring Cloud Eureka, API Gateway, Circuit Breaker, Resilience4j, Config Server,LoadBalancer

**What is springcloud?**

It is providing project for developing microservices
Microservices offering by springcloud:

**springcloudOpen Feign** : Spring Cloud OpenFeign is a declarative HTTP client library for building RESTful microservices. It integrates seamlessly with Spring Cloud and simplifies the development of HTTP clients by allowing you to create interfaces that resemble the API of the target service.

**springcloudNetflix Eureka**:Spring Cloud Eureka, part of the Spring Cloud Netflix project, is a service registry that allows microservices to register themselves and discover other services.
In Simple terms it act as a mediator for urls where we use id or name which we registered with eureka in all other microservices irrespective of the original URL.

**SpringCloud LoadBalancer** : it will balance the requests, A load balancer distributes user traffic across multiple instances of your applications.

**Spring cloud API Gateway** :It is a lightweight, reactive API gateway built on top of the Spring framework.It will take the responsibility of routing based on the url to Microservices.
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
So if we use feign client than no need to go with the webclient which was given by web flux

**Why we need open feign if we already have webclient to call the api(restful services)?**

Open feign will be useful to build the api's ina a declarative way. Just like how we call annotations. Its make our life easy and loosely coupled.

**Circuit breaker and their properties?**

Cb which helps to manage the fault tolerance it used resilence4j to make it happen.
we have 3 states: closed,open,Half-open
Properties:

**slidingWindowSize**: Configures the size of the sliding window which is used to record the outcome of calls when the CircuitBreaker is closed.
that is how many calls we want to consider to take  make the decision that we want to make the switch from closed to open. We can declare this with numeric value.
like for eg: if we are making 1000 calls per second between MS. than by setting slding window sizwe as 100 it checks the last 100 calls tha means 901 to 1000. based on that it will take the action.

**failureRateThreshold** : Configures the failure rate threshold in percentage. When the failure rate is equal or greater than the threshold the CircuitBreaker transitions to open and starts short-circuiting calls.

So after open the circuit we cant hod it open so we can configure some time based on that the cb will close again. The below property will help us to achieve that.


**waitDurationInOpenState**: The time that the CircuitBreaker should wait before transitioning from open to half-open.
We have a property to control the calls based on our req this will achiecve by using below prop
permittedNumberOfCallsInHalfOpenState : Configures the number of permitted calls when the CircuitBreaker is half open.
After setting the value to tha above prop it verifes again if the failuer rate is high the swith goes to open. if failure rate is low than it goes to closed

**How to configure Feignclient**
After downloading respective packages in the MS where we want to use the feign clinet.
Create a class for the Feign client inside respective miceroserviece (in our case we created inside Student-service)
use @feignclinet annotation on top of that class and declare the api-gateway name use ref as below

package com.infybuzz.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.infybuzz.response.AddressResponse;

//@FeignClient(value = "address-service",
//		path = "/api/address")
//public interface AddressFeignClient {
//
//	@GetMapping("/getById/{id}")
//	public AddressResponse getById(@PathVariable long id);

// the above code is using feign client with eureka server we are calling address method 
// but to use this class for all the micro service methos commonly and also we need to call the address method
// thorugh api gateway follow the below code

@FeignClient(value = "api-gateway")
public interface AddressFeignClient {

	@GetMapping("/address-service/api/address/getById/{id}")
	public AddressResponse getById(@PathVariable long id);	
}

**How to register specific MS to Eureka Server**

In application.prop file of MS add Eureka server URl
eureka.client.service-url.defaultZone:"add Eurkea server url"

and in application class add @EnableEurekaClient annotation.

**How to config api-agteway in a MS**
Create one more Project with api-gateway as dependency
Registery with eurekaclient
in application.prop --> add below properties
spring.cloud.gateway.discovery.locatior.enabled=true  --> this prop is reposnsible for ai-gateway to route to specific microservices based on the url
spring.cloud.gateway.discovery.locatior.lower-case-service-id = true  --> Our MS reigistered with Eureka as lower case but we declared it in respective MS appli.prop as lower case so to match with that we need this prop.

**How to inplement sleuth**

add the req dependecy in pom.xml of MS and api-gateway
Add the below prop in api-gateway application.prop for generating logs and service-id's for MS for each api
spring.sleuth.reactor.instrumentation-type=decorate-on-each
after we hit the api we will see the logs as below in the respective terminal
[api-gateway,3bgtuy567hgfbjnfj,ty657bdghejnv]
[application-name,traceid,spanid,zipkin-export]
Where,
Application-name = Name of the application
Traceid = each request and response traceid is same when calling same service or one service to another service.
Spanid = Span Id is printed along with Trace Id. Span Id is different every request and response calling one service to another service.
Zipkin-export = By default it is false. If it is true, logs will be exported to the Zipkin server.

Trace-id is same for the all MS for the individual api call. But span is different for different MS.


**How to start Zipkin server after we inmpleneted sleuth**
Once we downloaded the zipkin jar file go to that file postition and open cmd prompt and tun java -jar zipkinjarfile --> it will execute
After downloading we have to include zipkin url in the microservices and api-gateway 
The sleuth will give the trace-id of individual api call which zipkin will notify it with graphical represnetation


**Complete flow of how the feign client will send to soecific service using Eureka with api-gateway by example urls**

Setup
Eureka Server: Both student-service and address-service register themselves with Eureka.
API Gateway: The API Gateway is configured to automatically discover services registered with Eureka.
Example URLs
API Gateway Base URL: http://api-gateway:9090
Student Service: http://api-gateway:9090/student-service
Address Service: http://api-gateway:9090/address-service
Feign Client in Student Service
Your Feign client in the Student Service is configured to call the Address Service through the API Gateway:

@FeignClient(value = "api-gateway")
public interface AddressFeignClient {

    @GetMapping("/address-service/api/address/getById/{id}")
    public AddressResponse getById(@PathVariable long id);
}
Example Flow with URLs
Student Service Calls Address Service:

Feign Client Call: The Student Service makes a call to the Address Service using the Feign client.
URL: http://api-gateway:9090/address-service/api/address/getById/{id}
API Gateway Receives Request:

The API Gateway receives the request at http://api-gateway:9090/address-service/api/address/getById/{id}.
It recognizes the path /address-service/** and knows it should route this request to the address-service.
Service Discovery:

The API Gateway uses Eureka to discover the instances of the address-service.
Eureka provides the details of the available instances (e.g., http://address-service-instance:8081).
Routing to Address Service:

The API Gateway forwards the request to one of the instances of the address-service.
Example URL: http://address-service-instance:8081/api/address/getById/{id}
Address Service Processes Request:

The address-service processes the request and returns the response to the API Gateway.
The API Gateway then forwards the response back to the Student Service.
Example Usage in Student Service
Here's how you might use the AddressFeignClient in your Student Service:

@RestController
public class StudentController {
    @Autowired
    private AddressFeignClient addressFeignClient;

    @GetMapping("/students/{studentId}/address")
    public AddressResponse getStudentAddress(@PathVariable("studentId") Long studentId) {
        // Assume we have a method to get the addressId for the student
        Long addressId = getAddressIdForStudent(studentId);
        return addressFeignClient.getById(addressId);
    }
}
Summary
Feign Client: Sends a request to the API Gateway.
API Gateway: Routes the request to the appropriate microservice based on the path.
Eureka: Helps the API Gateway discover the actual instances of the microservice.
Microservice: Processes the request and returns the response.
This flow ensures that your Student Service can dynamically discover and call the Address Service through the API Gateway, making your microservices architecture more flexible and resilient.


