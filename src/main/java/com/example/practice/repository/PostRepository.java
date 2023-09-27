package com.example.practice.repository;

import com.example.practice.dto.PostResponse;
import com.example.practice.enity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {


    @Query("select new com.example.practice.dto.PostResponse(p.id, p.image, p.text, p.title, u.id) " +
            "from User u join u.posts p where u.id = :userId")
    List<PostResponse> findByUserId(Long userId);
}

