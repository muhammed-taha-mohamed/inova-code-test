package com.inova.dao;

import com.inova.model.Post;
import com.inova.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByUser_Id(Long userId, Pageable pageable);

    @Query("SELECT p FROM Post p JOIN Review r ON p.id = r.post.id GROUP BY p.id ORDER BY AVG(r.rating) DESC")
    Page<Post> findTopPosts(Pageable pageable);
}
