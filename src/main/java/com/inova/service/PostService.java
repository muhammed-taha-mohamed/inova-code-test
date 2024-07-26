package com.inova.service;

import com.inova.dto.PostDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {

    PostDTO addPost(PostDTO postDTO);
    Page<PostDTO> listUserPosts(Long userId, int page, int size);
    List<PostDTO> listTopPosts(int page, int size);
}
