package com.example.practice.service;

import com.example.practice.dto.PaginationPostResponse;
import com.example.practice.dto.PostResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface InstagramFeedService {

     List<PostResponse> getUsersPost();


     PaginationPostResponse getUsersPostByPagination( int currentPage, int pageSize, LocalDate dateOfStart, LocalDate dateOfFinish);
}
