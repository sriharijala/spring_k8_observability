package com.sjala.springboot3.jpa.hibernate.controllers;

import com.sjala.springboot3.jpa.hibernate.model.Customer;
import com.sjala.springboot3.jpa.hibernate.model.Review;
import com.sjala.springboot3.jpa.hibernate.services.UserService;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Tag(name = "User", description = "User management APIs")
@RestController
@RequestMapping("/v1")
public class UserController {

    @Autowired
    private UserService userService;

	@Autowired
	private WebClient webClient;

	@Autowired
	private ObservationRegistry observationRegistry;

    @Autowired
    Tracer tracer;
    
    @Operation(
	  	      summary = "List all customer",
	  	      description = "It returns all customer details.",
	  	      tags = { "User" })
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Customer.class), mediaType = "application/json") }),
        @ApiResponse(responseCode = "204", description = "No users found", content = { @Content(schema = @Schema()) })
      })
    @GetMapping("/")
    public  ResponseEntity<List<Customer>>  getAllUsers() {
    	
    	Span echoSpan = tracer.nextSpan().name("calling-controller-getAllUsers").start();
    	 
        List<Customer> users = userService.getAllUsers();
        
        echoSpan.end();
        
        if(users.size() ==0)
        	    return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
        else
        		return new ResponseEntity<>(users,HttpStatus.OK);
    }

    @Operation(
	  	      summary = "Get customer details",
	  	      description = "Returns user details by user idr",
	  	      tags = { "User" })
    @ApiResponses({
      @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Customer.class), mediaType = "application/json") }),
      @ApiResponse(responseCode = "204", description = "No users found", content = { @Content(schema = @Schema()) })
    })
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Customer>> getUserById(@PathVariable Long id) {
    	
    	Span echoSpan = tracer.nextSpan().name("calling-controller-getUserById").start();    	
    	
        Optional<Customer> user = userService.getUserById(id);

        if(user == null)
		{
			echoSpan.end();
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}
		else
		{

			List<Review> reviews =
					Observation.createNotStarted("webclient.custom.operation", observationRegistry)
					.observe(() ->
							webClient.get()
									.uri("http://review-svc/review/v1/user/" + id)
									.retrieve()
									.bodyToMono(List.class)
									.block()
					);


			int count = reviews.size();

			if(count > 0) {
				user.get().setReviews(reviews);
			}

		}


		echoSpan.end();
		return new ResponseEntity<>(user, HttpStatus.OK);
    }



    @Operation(
	  	      summary = "Post a new review",
	  	      description = "Customer can add new review.",
	  	      tags = { "User" })
    @ApiResponses({
      @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Customer.class), mediaType = "application/json") }),
      @ApiResponse(responseCode = "500", description = "Something went wrong, please retry.", content = { @Content(schema = @Schema()) })
    })
	@PostMapping(path = "/", produces = "application/json", consumes = "application/json")
	public ResponseEntity<Customer> addUser(
			@RequestBody(description = "User details", required = true,
                         content = @Content(
                        			schema=@Schema(implementation = Customer.class))) 
			@Valid @org.springframework.web.bind.annotation.RequestBody Customer user
		)
	{

    	Span echoSpan = tracer.nextSpan().name("calling-controller-addUser").start(); 
    	
    	Optional<Customer> savedUser = userService.addUser(user);
    	
    	echoSpan.end();
    	
		 if(user == null)
	  	     return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
	  	 else 
	  	     return new ResponseEntity<>(savedUser.get(), HttpStatus.OK);
		 	
	}

}
