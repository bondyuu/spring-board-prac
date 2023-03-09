package com.example.boardprac.controller;

import com.example.boardprac.auth.UserDetailsImpl;
import com.example.boardprac.dto.PostRequestDto;
import com.example.boardprac.service.PostService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/save")
    public ResponseEntity<?> savePost(@RequestBody PostRequestDto requestDto,
                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.savePost(requestDto, userDetails);
    }

    @GetMapping
    public ResponseEntity<?> searchPost(@RequestParam String title,
                                  @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return postService.searchPost(title, pageable);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<?> editPost(@RequestBody PostRequestDto requestDto, @PathVariable(name = "postId") long id,
                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.editPost(id, requestDto, userDetails);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable(name = "postId") long id,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.deletePost(id, userDetails);
    }

    @PostMapping("/like/{postId}")
    public ResponseEntity<?> likePost(@PathVariable(name = "postId") long id,
                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.likePost(id, userDetails);
    }
}
