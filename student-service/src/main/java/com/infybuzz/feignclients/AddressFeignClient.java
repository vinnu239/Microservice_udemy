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
