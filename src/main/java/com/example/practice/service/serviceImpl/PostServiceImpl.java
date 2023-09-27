package com.example.practice.service.serviceImpl;

import com.example.practice.dto.PostRequest;
import com.example.practice.dto.PostResponse;
import com.example.practice.dto.SimpleResponse;
import com.example.practice.enity.Post;
import com.example.practice.enity.User;
import com.example.practice.exceptions.BadCredentialException;
import com.example.practice.exceptions.BadRequestException;
import com.example.practice.exceptions.NotFoundException;
import com.example.practice.repository.PostRepository;
import com.example.practice.repository.UserRepository;
import com.example.practice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    public SimpleResponse savePost(PostRequest postRequest) {

        User user =currentUser();
        Post post=new Post();
        post.setText(postRequest.getText());
        post.setTitle(postRequest.getTitle());
        post.setLocalDateTime(LocalDateTime.now());
        post.setImage(postRequest.getImage());
        user.addPost(post);
        userRepository.save(user);
        postRepository.save(post);
        return new SimpleResponse(HttpStatus.OK, "Post with id: "+post.getId()+" successfully saved! ");


    }

    @Override
    public SimpleResponse updatePost(Long postId, PostRequest postRequest) {
        User user = currentUser();
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Post with id: " + postId + " not found!"));
        if(!user.getPosts().contains(post)){
            throw new BadRequestException("This is a not user's post");
        }
        post.setImage(postRequest.getImage());
        post.setText(postRequest.getText());
        post.setTitle(postRequest.getTitle());
        postRepository.save(post);
        return new SimpleResponse(HttpStatus.OK, "Post with id: "+post.getId()+" successfully updated!");


    }

    @Override
    public List<PostResponse> getAllPostsByUserId(Long userId) {
       return postRepository.findByUserId(userId);
    }

    @Override
    public SimpleResponse deletePost(Long postId) {
        User currentUser = currentUser();

        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Post with id: " + postId + " not found!"));
        if(!currentUser.getPosts().contains(post)){
            throw new BadRequestException("This is a not user's post");
        }
        User user = userRepository.findUserByPostId(postId);
        user.removePost(post);
        postRepository.deleteById(postId);
        return new SimpleResponse(HttpStatus.OK, "Post with id: "+post.getId()+" successfully deleted! ");
    }

    @Override
    public PostResponse getPostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("Post with id: " + postId + " not found!"));
        User user = userRepository.findUserByPostId(postId);
        return new PostResponse(post.getId(), post.getImage(), post.getText(), post.getTitle(), user.getId());

    }
    private User currentUser() {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.getUserByEmail((currentUser.getName()))
                .orElseThrow(() -> new BadCredentialException("Forbidden for non-registered users"));
    }
}
