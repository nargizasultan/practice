package com.example.practice.service.serviceImpl;

import com.example.practice.dto.SimpleResponse;
import com.example.practice.enity.User;
import com.example.practice.exceptions.BadCredentialException;
import com.example.practice.exceptions.NotFoundException;
import com.example.practice.repository.UserRepository;
import com.example.practice.service.ApplicationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class ApplicationServiceImpl implements ApplicationService {

    private final UserRepository userRepository;


    @Override
    public SimpleResponse follow(Long receiverId) {

        User user = currentUser();
        User receiver = findUserById(receiverId);
        if (userRepository.isUserFollowing( user.getId(), receiverId)) {
            user.addToFriends(receiver);
            receiver.addToFriends(user);
        }

        user.addToFollows(receiver);
        receiver.addToFollowers(user);

        userRepository.save(user);
        return new SimpleResponse(HttpStatus.OK, "You have successfully subscribed to the user with id: " + receiver.getId());

    }
    @Transactional
    @Override
    public SimpleResponse unfollow(Long receiverId) {

        User user = currentUser();
        User receiver = findUserById(receiverId);

        user.removeFromFollows(receiver);
        receiver.removeFromFollowers(user);
        user.removeFromFriends(receiver);
        receiver.removeFromFriends(user);
        userRepository.save(user);


        return new SimpleResponse(HttpStatus.OK, "You have successfully removed user with id: " + receiver.getId() + " from follows");
    }

    private User currentUser() {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.getUserByEmail((currentUser.getName()))
                .orElseThrow(() -> new BadCredentialException("Forbidden for non-registered users"));
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with id: " + userId + " not found!"));
    }
}
