package com.inova.service.impl;

import com.inova.dao.PostRepository;
import com.inova.dao.ReviewRepository;
import com.inova.dao.UserRepository;
import com.inova.dto.ReviewDTO;
import com.inova.error.exceptions.InovaException;
import com.inova.mapper.ReviewMapper;
import com.inova.model.Review;
import com.inova.service.ReviewService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    private final PostRepository postRepository;

    private UserRepository userRepository;

    private ReviewMapper reviewMapper;

    @Override
    public ReviewDTO addReview(ReviewDTO reviewDTO) {
        log.info("Adding review for post ID: {}", reviewDTO.getPostId());

        try {
            Review review = reviewMapper.toEntity(reviewDTO);
            review.setPost(postRepository.findById(reviewDTO.getPostId()).orElseThrow(() -> {
                log.error("Post not found for ID: {}", reviewDTO.getPostId());
                return new RuntimeException("Post not found");
            }));
            review.setUser(userRepository.findById(reviewDTO.getUserId()).orElseThrow(() -> {
                log.error("User not found for ID: {}", reviewDTO.getUserId());
                return new RuntimeException("User not found");
            }));

            Review savedReview = reviewRepository.save(review);
            log.info("Review added successfully with ID: {}", savedReview.getId());
            return reviewMapper.toDto(savedReview);
        } catch (Exception e) {
            log.error("Error adding review: {}", e.getMessage());
            throw new InovaException(e.getMessage());
        }
    }
}
