package com.sjala.micro.service.review.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "User Model Information")
public class Customer {

    @Schema( description = "User Id", example = "1")
    private Long id;

    @Schema(description = "User Firstname ", example = "John")
    private String firstName;

    @Schema(description = "User Last name ", example = "Smith")
    private String lastName;

    @Schema(description = "User email address", example = "Smith")
    private String email;


}
