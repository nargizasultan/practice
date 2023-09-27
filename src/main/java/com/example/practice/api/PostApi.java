package com.example.practice.api;

import com.example.practice.dto.PostRequest;
import com.example.practice.dto.PostResponse;
import com.example.practice.dto.SimpleResponse;

import com.example.practice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostApi {
    private final PostService postService;

    @GetMapping("/{postId}")
    public PostResponse getPostById(@PathVariable Long postId){
        return postService.getPostById(postId);
    }
    @GetMapping("/all/{userId}")
    public List<PostResponse> getPostByUserId(@PathVariable Long userId){
        return postService.getAllPostsByUserId(userId);
    }
    @PostMapping()
    public SimpleResponse savePost( @RequestBody PostRequest postRequest){
       return postService.savePost(postRequest);

    }
    @PutMapping("/{postId}")
    public SimpleResponse updatePost(@PathVariable Long postId, @RequestBody PostRequest postRequest){
        return postService.updatePost(postId, postRequest);
    }
    @DeleteMapping("/{postId}")
    public SimpleResponse deletePost(@PathVariable Long postId){
        return postService.deletePost(postId);
    }



}
