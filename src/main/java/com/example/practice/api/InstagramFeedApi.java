package com.example.practice.api;

import com.example.practice.dto.PaginationPostResponse;
import com.example.practice.dto.PostResponse;
import com.example.practice.service.InstagramFeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/instagram_feed")
@RequiredArgsConstructor
public class InstagramFeedApi {

    private final InstagramFeedService instagramFeedService;

    @GetMapping
    public List<PostResponse> getAllPosts() {
        return instagramFeedService.getUsersPost();
    }

    @GetMapping("/pagination")
    public PaginationPostResponse getAllPostsByPagination(@RequestParam(required = false, defaultValue = "1") int currentPage,
                                                          @RequestParam(required = false, defaultValue = "10") int pageSize,
                                                          @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateOfStart,
                                                          @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateOfFinish) {
        return instagramFeedService.getUsersPostByPagination( currentPage, pageSize,dateOfStart, dateOfFinish );
    }


}
