package com.example.practice.service.serviceImpl;

import com.example.practice.dto.PaginationPostResponse;
import com.example.practice.dto.PostResponse;
import com.example.practice.enity.Post;
import com.example.practice.enity.User;
import com.example.practice.exceptions.BadCredentialException;

import com.example.practice.exceptions.BadRequestException;
import com.example.practice.exceptions.NotFoundException;
import com.example.practice.repository.PostRepository;
import com.example.practice.repository.UserRepository;
import com.example.practice.service.InstagramFeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InstagramFeedServiceImpl implements InstagramFeedService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    public List<PostResponse> getUsersPost() {

        return postRepository.findLatestPostsFromFollowedUsers(currentUser().getId());
    }

    @Override
    public PaginationPostResponse getUsersPostByPagination( int currentPage, int pageSize, LocalDate dateOfStart, LocalDate dateOfFinish) {

        List<Post> all = postRepository.findAll(currentUser().getId());

        if (dateOfFinish != null && dateOfFinish.isAfter(LocalDate.now())) {
            throw new BadRequestException("Invalid date");
        }
        LocalDateTime startDate = null;
        LocalDateTime endDate = null;

        if (!all.isEmpty()) {

            if (dateOfStart == null && dateOfFinish != null) {

                startDate = all.get(0).getLocalDateTime();
                endDate = dateOfFinish.atStartOfDay().plusDays(1);

            } else if (dateOfStart != null && dateOfFinish == null) {

                startDate = dateOfStart.atStartOfDay();
                endDate = all.get(all.size() - 1).getLocalDateTime().plusDays(1);

            } else if (dateOfStart == null && dateOfFinish == null) {

                startDate = all.get(0).getLocalDateTime();
                endDate = all.get(all.size() - 1).getLocalDateTime().plusDays(1);

            } else if (dateOfStart != null && dateOfFinish != null) {

                startDate = dateOfStart.atStartOfDay();
                endDate = dateOfFinish.atStartOfDay().plusDays(1);
            }

            Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
            Page<PostResponse> allPosts = postRepository.getAllPosts(pageable, currentUser().getId(), startDate, endDate);
            return new PaginationPostResponse(allPosts.getContent(), allPosts.getNumber() + 1, allPosts.getTotalPages());

        }else {
            throw new NotFoundException("There are no entries available!");
        }
    }

    private User currentUser() {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.getUserByEmail((currentUser.getName()))
                .orElseThrow(() -> new BadCredentialException("Forbidden for non-registered users"));
    }


}
