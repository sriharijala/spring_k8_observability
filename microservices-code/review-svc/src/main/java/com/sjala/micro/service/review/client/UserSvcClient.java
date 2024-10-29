package com.sjala.micro.service.review.client;

import com.sjala.micro.service.review.model.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.Optional;

@HttpExchange
public interface UserSvcClient {

    @GetExchange("/user/v1/user/{id}")
    ResponseEntity<Optional<Customer>> getUserById(@PathVariable("id")  Long id);
}