package com.inova.controller;

import com.inova.dto.ReviewDTO;
import com.inova.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@AllArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewDTO> addReview(@RequestBody ReviewDTO reviewDTO) {
        ReviewDTO savedReview = reviewService.addReview(reviewDTO);
        return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
    }
}
