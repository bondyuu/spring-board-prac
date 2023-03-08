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
    public ResponseEntity<?> save(@RequestBody PostRequestDto requestDto,
                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.save(requestDto, userDetails);
    }

    @GetMapping
    public ResponseEntity<?> find(@RequestParam String title,
                                  @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return postService.find(title, pageable);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<?> edit(@RequestBody PostRequestDto requestDto, @PathVariable(name = "postId") long id) {
        return postService.update(id, requestDto);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> delete(@PathVariable(name = "postId") long id) {
        return postService.delete(id);
    }

    @PostMapping("/like/{postId}")
    public ResponseEntity<?> like(@PathVariable(name = "postId") long id,
                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.like(id, userDetails);
    }
}
