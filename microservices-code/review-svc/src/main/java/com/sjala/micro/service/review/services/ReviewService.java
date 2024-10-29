package com.sjala.micro.service.review.services;

import java.util.List;
import java.util.Optional;

import com.sjala.micro.service.review.model.Review;
import com.sjala.micro.service.review.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> findAllPosts() {

        List<Review> reviews = (List<Review>) reviewRepository.findAll();
        return reviews;
    }

    public Optional<Review> findPostById(Long id) {
        Optional<Review> post = reviewRepository.findById(id);
        return post;
    }

    @Transactional
    public Optional<Review> addReview(Review review) {
        Review post = reviewRepository.save(review);
        return Optional.of(post);
    }

    public List<Review> findReviewsByUserId(Long userId) {
        List<Review> reviews = (List<Review>) reviewRepository.findReviewsByUserId(userId);
        return reviews;
    }
}
