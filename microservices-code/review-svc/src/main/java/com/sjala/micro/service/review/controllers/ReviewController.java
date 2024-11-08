package com.sjala.micro.service.review.controllers;

import com.sjala.micro.service.review.client.UserSvcClient;
import com.sjala.micro.service.review.model.Customer;
import com.sjala.micro.service.review.model.Review;
import com.sjala.micro.service.review.services.ReviewService;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Tag(name = "Review", description = "Review management APIs")
@RestController
@RequestMapping("/v1")
public class ReviewController {

	@Autowired
	private ReviewService postService;

	@Autowired
	private WebClient webClient;

	@Autowired
	private ObservationRegistry observationRegistry;

	@Autowired
    Tracer tracer;
   

	@Operation(summary = "Retrieve a all reviews", description = "Get all posts. The response is list of Post objects.", tags = {
			"Review" })
	@ApiResponses({
	    @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Review.class), mediaType = "application/json") }),
	    @ApiResponse(responseCode = "204", description = "No reviews found", content = { @Content(schema = @Schema()) })
	  })
	@GetMapping("/")
	public ResponseEntity<List<Review>> getPosts() {
		
		Span echoSpan = tracer.nextSpan().name("calling-controller-getPosts").start();
		
		List<Review> reviews = postService.findAllPosts();
		
		echoSpan.end();
		
		if(reviews == null)
	      return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
	    else 
	     return new ResponseEntity<>(reviews, HttpStatus.OK);
	}


	@Operation(
  	      summary = "Retrieve a Post  by Id",
  	      description = "Get a post object by specifying its id. The response is Post object details .",
  	      tags = { "Review" })
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Review.class), mediaType = "application/json") }),
        @ApiResponse(responseCode = "204", description = "The Post with given Id was not found.", content = { @Content(schema = @Schema()) })
      })
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Review>> getLocationById(@PathVariable Long id) {
		
		Span echoSpan = tracer.nextSpan().name("calling-controller-getLocationById").start();
		
        Optional<Review> post = postService.findPostById(id);
        
        echoSpan.end();
        
        if(post == null)
  	      return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
  	    else 
  	     return new ResponseEntity<>(post, HttpStatus.OK);
    }

	
	@Operation(
	  	      summary = "Add new review",
	  	      description = "User can add new review.",
	  	      tags = { "Review" })
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Review.class), mediaType = "application/json") }),
        @ApiResponse(responseCode = "204", description = "The customer with given Id was not found.", content = { @Content(schema = @Schema()) }),
        @ApiResponse(responseCode = "500", description = "Something went wrong, please retry.", content = { @Content(schema = @Schema()) })
      })
	@PostMapping(path = "/",  produces = "application/json", consumes = "application/json")
	public ResponseEntity<Optional<Review>> submitReview(
			@RequestBody(description = "Review to add.", required = true,
             content = @Content(
                     schema=@Schema(implementation = Review.class))) 
			@Valid @org.springframework.web.bind.annotation.RequestBody Review review
		)
	{
		 Span echoSpan = tracer.nextSpan().name("calling-controller-submitReview").start();

		 Optional<Review> postedReview = null;
		 
		 review.setPostDate(LocalDateTime.now());

		 postedReview = postService.addReview(review);

		 echoSpan.end();
		 
		 if(postedReview == null)
	  	      return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
	  	    else 
	  	     return new ResponseEntity<>(postedReview, HttpStatus.OK);
		 	
	}

	@Operation(summary = "Retrieve all reviews by user id", description = "Get all posts by a given user. The response is list of Post objects.", tags = {
			"Review" })
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Review.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "204", description = "No reviews found", content = { @Content(schema = @Schema()) })
	})
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Review>> getReviewsByUserId(@PathVariable Long userId) {

		Span echoSpan = tracer.nextSpan().name("calling-controller-getReviewsByUserId").start();

		List<Review> reviews = postService.findReviewsByUserId(userId);

		echoSpan.end();

		if(reviews == null)
			return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<>(reviews, HttpStatus.OK);
	}

}
