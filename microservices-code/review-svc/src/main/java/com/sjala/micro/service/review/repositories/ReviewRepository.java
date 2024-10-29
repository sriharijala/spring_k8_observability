package com.sjala.micro.service.review.repositories;

import java.util.List;

import com.sjala.micro.service.review.model.Review;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ReviewRepository extends CrudRepository<Review,Long> {

    @Query(
            value = "SELECT * FROM review r WHERE r.reviewer_id = :customerID",
            nativeQuery = true)
    List<Review> findReviewsByUserId(@Param("customerID") Long customerID);
}
