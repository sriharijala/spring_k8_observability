package com.sjala.springboot3.jpa.hibernate.client;

import com.sjala.springboot3.jpa.hibernate.model.Review;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;


@HttpExchange
public interface ReviewSvcClient {

    @GetExchange("/review/v1/review/user/{id}")
    ResponseEntity<List<Review>> getReviewsUserById(@PathVariable("id")  Long id);
}