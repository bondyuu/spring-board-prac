package com.example.boardprac.controller;

import com.example.boardprac.auth.UserDetailsImpl;
import com.example.boardprac.service.AdminService;
import com.example.boardprac.service.PostService;
import com.example.boardprac.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final PostService postService;
    private final AdminService adminService;

    @GetMapping("/main")
    public ResponseEntity<?> getMain(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return adminService.getMain(userDetails);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers(@RequestParam String email,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails,
                                      @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return userService.getUsers(email, userDetails, pageable);
    }
    @GetMapping("/users/{userId}")
    public ResponseEntity<?> getUserDetail(@PathVariable(name = "userId") long id,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return adminService.getUserDetail(id, userDetails);
    }

    @GetMapping("/users/{userId}/posts")
    public ResponseEntity<?> getPostsByUser(@PathVariable(name = "userId") long id,
                                       @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.getPostsByUser(id, userDetails);
    }

    @GetMapping("/posts")
    public ResponseEntity<?> getPosts(@RequestParam String title,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails,
                                      @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return adminService.searchPost(title, userDetails, pageable);
    }
}
