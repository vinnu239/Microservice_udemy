package com.infybuzz.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.infybuzz.entity.Student;
import com.infybuzz.feignclients.AddressFeignClient;
import com.infybuzz.repository.StudentRepository;
import com.infybuzz.request.CreateStudentRequest;
import com.infybuzz.response.AddressResponse;
import com.infybuzz.response.StudentResponse;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class StudentService {

	Logger logger = LoggerFactory.getLogger(StudentService.class);
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	WebClient webClient;
	
	@Autowired
	AddressFeignClient addressFeignClient;
	
	@Autowired
	CommonService commonService;

	public StudentResponse createStudent(CreateStudentRequest createStudentRequest) {

		Student student = new Student();
		student.setFirstName(createStudentRequest.getFirstName());
		student.setLastName(createStudentRequest.getLastName());
		student.setEmail(createStudentRequest.getEmail());
		
		student.setAddressId(createStudentRequest.getAddressId());
		student = studentRepository.save(student);
		
		StudentResponse studentResponse = new StudentResponse(student);
		
		//studentResponse.setAddressResponse(getAddressById(student.getAddressId()));
		
		studentResponse.setAddressResponse(commonService.getAddressById(student.getAddressId()));

		return studentResponse;
	}
	
	public StudentResponse getById (long id) {
		logger.info("Inside student geyByid");
		Student student = studentRepository.findById(id).get();
		StudentResponse studentResponse = new StudentResponse(student);
		
		//studentResponse.setAddressResponse(getAddressById(student.getAddressId()));
		
		studentResponse.setAddressResponse(commonService.getAddressById(student.getAddressId()));
		
		return studentResponse;
	}
	
	// the below code was moved to seperate class becuase due to concept of Spring Apo proxy
	// for the same class we will not call same method multiple time in our case it is getAddressById
	// internally resellience4j will use spring AOP only(Aspect Oriented Programming)
	
	/*
	 * @CircuitBreaker(name = "addressService", fallbackMethod =
	 * "fallbackGetAddressById") public AddressResponse getAddressById (long
	 * addressId) { AddressResponse addressResponse =
	 * addressFeignClient.getById(addressId);
	 * 
	 * return addressResponse; }
	 * 
	 * public AddressResponse fallbackGetAddressById (long addressId, Throwable th)
	 * { return new AddressResponse(); }
	 */
}
