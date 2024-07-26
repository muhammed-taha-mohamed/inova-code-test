package com.inova.controller;

import com.inova.dto.PostDTO;
import com.inova.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostDTO> addPost(@RequestBody PostDTO postDTO) {
        PostDTO savedPost = postService.addPost(postDTO);
        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<PostDTO>> listUserPosts(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<PostDTO> posts = postService.listUserPosts(userId, page, size);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/top")
    public ResponseEntity<List<PostDTO>> listTopPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<PostDTO> topPosts = postService.listTopPosts(page, size);
        return new ResponseEntity<>(topPosts, HttpStatus.OK);
    }
}
