package com.example.practice.api;

import com.example.practice.dto.SimpleResponse;
import com.example.practice.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationApi {

    private final ApplicationService applicationService;

    @PostMapping("/{receiverId}/follow")
    public SimpleResponse follow(@PathVariable Long receiverId) {
        return applicationService.follow(receiverId);
    }

    @PostMapping("/{receiverId}/unfollow")
    public SimpleResponse unfollow(@PathVariable Long receiverId) {
        return applicationService.unfollow(receiverId);
    }

}
