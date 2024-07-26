package com.inova.service.impl;

import com.inova.dao.PostRepository;
import com.inova.dao.UserRepository;
import com.inova.dto.PostDTO;
import com.inova.error.exceptions.InovaException;
import com.inova.mapper.PostMapper;
import com.inova.model.Post;
import com.inova.service.PostService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;

    @Override
    public PostDTO addPost(PostDTO postDTO) {
        log.info("Adding post for user ID: {}", postDTO.getUserId());
        try {
            Post post = postMapper.toEntity(postDTO);
            post.setUser(userRepository.findById(postDTO.getUserId()).orElseThrow(() -> {
                log.error("User not found with ID: {}", postDTO.getUserId());
                return new InovaException("User not found");
            }));
            Post savedPost = postRepository.save(post);
            log.info("Post added successfully with ID: {}", savedPost.getId());
            return postMapper.toDto(savedPost);
        } catch (Exception e) {
            log.error("Error adding post: {}", e.getMessage(), e);
            throw new InovaException(e.getMessage());
        }
    }

    @Override
    public Page<PostDTO> listUserPosts(Long userId, int page, int size) {
        log.info("Listing posts for user ID: {} | Page: {} | Size: {}", userId, page, size);
        try {
            Page<Post> postPage = postRepository.findAllByUser_Id(userId, PageRequest.of(page, size));
            log.info("Retrieved {} posts for user ID: {}", postPage.getTotalElements(), userId);
            return postMapper.toResponsePageable(postPage);
        } catch (Exception e) {
            log.error("Error retrieving posts for user ID: {}: {}", userId, e.getMessage(), e);
            throw new InovaException(e.getMessage());
        }
    }

    @Override
    public List<PostDTO> listTopPosts(int page, int size) {
        log.info("Listing top posts | Page: {} | Size: {}", page, size);
        try {
            Page<Post> postPage = postRepository.findTopPosts(PageRequest.of(page, size));
            log.info("Retrieved {} top posts", postPage.getTotalElements());
            return postPage.getContent().stream()
                    .map(postMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error retrieving top posts: {}", e.getMessage(), e);
            throw new InovaException(e.getMessage());
        }
    }
}
