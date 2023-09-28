package com.example.practice.repository;

import com.example.practice.dto.PostResponse;
import com.example.practice.dto.StudentResponse;
import com.example.practice.enity.Post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {


    @Query("select new com.example.practice.dto.PostResponse(p.id, p.image, p.text, p.title, u.id) " +
            "from User u join u.posts p where u.id = :userId")
    List<PostResponse> findByUserId(Long userId);

    @Query("select p from User u join u.follows f join f.posts p where u.id=:userId")
    List<Post> findAll(Long userId);


    @Query("select new com.example.practice.dto.PostResponse(p.id, p.image, p.text, p.title,p.user.id) from User u " +
            "join u.follows f join f.posts p where u.id=:userId order by p.localDateTime desc ")
    List<PostResponse> findLatestPostsFromFollowedUsers(Long userId);

    @Query("select new com.example.practice.dto.PostResponse(p.id, p.image, p.text, p.title,p.user.id) from User u " +
            "join u.follows f join f.posts p where u.id=:userId and p.localDateTime between  :dateOfStart and :dateOfFinish  order by p.localDateTime desc ")
    Page<PostResponse> getAllPosts(Pageable pageable, Long userId, LocalDateTime dateOfStart, LocalDateTime dateOfFinish);
}

