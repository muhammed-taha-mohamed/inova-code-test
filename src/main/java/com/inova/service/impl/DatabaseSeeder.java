package com.inova.service.impl;

import com.inova.dao.PostRepository;
import com.inova.dao.ReviewRepository;
import com.inova.dao.UserRepository;
import com.inova.model.Post;
import com.inova.model.Review;
import com.inova.model.User;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@Slf4j
@AllArgsConstructor
public class DatabaseSeeder {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ReviewRepository reviewRepository;
    private final Random random = new Random();
    private static final int USER_BATCH_SIZE = 10;
    private static final int POST_BATCH_SIZE = 1000;
    private static final int REVIEW_BATCH_SIZE = 1000;

    @PostConstruct
    public void seek() {
        log.info("Starting database seeding...");

        seedUsers(100);
        seedPosts(50000);
        seedReviews(20000);

        log.info("Database seeding completed.");
    }

     
    public void seedUsers(int count) {
        log.info("Seeding {} users...", count);

        List<User> usersBatch = IntStream.rangeClosed(1, count)
                .mapToObj(i -> {
                    User user = new User();
                    user.setUsername("user" + i);
                    return user;
                })
                .collect(Collectors.toList());

        // Save users in batches
        for (int i = 0; i < usersBatch.size(); i += USER_BATCH_SIZE) {
            int end = Math.min(i + USER_BATCH_SIZE, usersBatch.size());
            userRepository.saveAll(usersBatch.subList(i, end));
            log.info("Created {} users", end);
        }

        log.info("Finished seeding users.");
    }

     
    public void seedPosts(int count) {
        List<User> users = userRepository.findAll();
        log.info("Seeding {} posts...", count);

        List<Post> postsBatch = IntStream.rangeClosed(1, count)
                .mapToObj(i -> {
                    Post post = new Post();
                    post.setTitle("Post Title " + i);
                    post.setBody("This is the body of post " + i);
                    post.setUser(users.get(random.nextInt(users.size()))); // Assign a random user
                    return post;
                })
                .collect(Collectors.toList());

        // Save posts in batches
        for (int i = 0; i < postsBatch.size(); i += POST_BATCH_SIZE) {
            int end = Math.min(i + POST_BATCH_SIZE, postsBatch.size());
            postRepository.saveAll(postsBatch.subList(i, end));
            log.info("Created {} posts", end);
        }

        log.info("Finished seeding posts.");
    }

     
    public void seedReviews(int count) {
        List<Post> posts = postRepository.findAll();
        List<User> users = userRepository.findAll();
        log.info("Seeding {} reviews...", count);

        List<Review> reviewsBatch = IntStream.rangeClosed(1, count)
                .mapToObj(i -> {
                    Review review = new Review();
                    review.setRating(random.nextInt(5) + 1); // Rating between 1 and 5
                    review.setComment("This is review " + i);
                    review.setPost(posts.get(random.nextInt(posts.size()))); // Assign a random post
                    review.setUser(users.get(random.nextInt(users.size()))); // Assign a random user
                    return review;
                })
                .collect(Collectors.toList());

        // Save reviews in batches
        for (int i = 0; i < reviewsBatch.size(); i += REVIEW_BATCH_SIZE) {
            int end = Math.min(i + REVIEW_BATCH_SIZE, reviewsBatch.size());
            reviewRepository.saveAll(reviewsBatch.subList(i, end));
            log.info("Created {} reviews", end);
        }

        log.info("Finished seeding reviews.");
    }
}
