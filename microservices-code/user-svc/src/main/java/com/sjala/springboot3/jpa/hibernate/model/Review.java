package com.sjala.springboot3.jpa.hibernate.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "Review Model Information")
public class Review {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Address of the location", example = "123")
    private Long id;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Date of submission of date", example = "2024-10-04T20:26:27.258Z")
    private LocalDateTime postDate;
    
    @Schema(description = "Product review comments", example = "Product is awesome")
    private String details;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Submited by customer id", example = "123")
    private Long reviewerId;    

}
