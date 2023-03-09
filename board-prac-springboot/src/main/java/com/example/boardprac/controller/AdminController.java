package com.example.boardprac.controller;

import com.example.boardprac.auth.UserDetailsImpl;
import com.example.boardprac.service.PostService;
import com.example.boardprac.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final PostService postService;

    @GetMapping("/users")
    private ResponseEntity<?> getUsers(@RequestParam String email,
                                       @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.getUsers(email, userDetails);
    }

}
