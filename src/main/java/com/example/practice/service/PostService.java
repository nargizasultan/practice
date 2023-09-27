package com.example.practice.service;

import com.example.practice.dto.PostRequest;
import com.example.practice.dto.PostResponse;
import com.example.practice.dto.SimpleResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
    SimpleResponse savePost( PostRequest postRequest);
    SimpleResponse updatePost(Long id, PostRequest postRequest);

    List<PostResponse>getAllPostsByUserId(Long userId);

    SimpleResponse deletePost(Long id);

    PostResponse getPostById(Long postId);
}
