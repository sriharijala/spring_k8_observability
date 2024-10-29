package com.sjala.springboot3.jpa.hibernate.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sjala.springboot3.jpa.hibernate.model.Review;
import com.sjala.springboot3.jpa.hibernate.model.Customer;
import com.sjala.springboot3.jpa.hibernate.repositories.UserRepository;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import jakarta.validation.Valid;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    Tracer tracer;

    public List<Customer> getAllUsers() {
    	
    	Span echoSpan = tracer.nextSpan().name("calling-UserService-getAllUsers").start();
    	
    	List<Customer> customers =  (List<Customer>) userRepository.findAll();
    	
    	echoSpan.end();
		
		return customers;
    }

    public Optional<Customer> getUserById(Long id) {

		Span echoSpan = tracer.nextSpan().name("calling-UserService-getUserById").start();
		
		Optional<Customer> customer = userRepository.findById(id);
		
		echoSpan.end();
		
		return customer;
    }
    

	public Optional<Customer> addUser(@Valid Customer user) {
		
		Span echoSpan = tracer.nextSpan().name("calling-UserService-addUser").start();
		
		Customer savedUser = userRepository.save(user);
		
		echoSpan.end();
		
		return Optional.of(savedUser);
	}
}
