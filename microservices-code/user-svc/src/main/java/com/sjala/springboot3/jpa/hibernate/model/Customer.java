package com.sjala.springboot3.jpa.hibernate.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "User Model Information")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Location Id", example = "123")
    private Long id;

    @Schema(description = "User Firstname ", example = "John")
    private String firstName;
    
    @Schema(description = "User Last name ", example = "Smith")
    private String lastName;
    
    @Schema(description = "User email address", example = "Smith")
    private String email;

    
    @Transient
    @Schema(accessMode = Schema.AccessMode.READ_ONLY,  description = "User reviews list")
    private List<Review> reviews;

}
