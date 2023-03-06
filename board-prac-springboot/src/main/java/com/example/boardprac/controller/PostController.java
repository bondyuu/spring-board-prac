package com.example.boardprac.controller;

import com.example.boardprac.auth.UserDetailsImpl;
import com.example.boardprac.dto.PostRequestDto;
import com.example.boardprac.service.PostService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> findAll() {
        return postService.findAll();
    }

    @PutMapping("/{postId}")
    public ResponseEntity<?> edit(@RequestBody PostRequestDto requestDto, @PathVariable(name = "postId") long id) {
        return postService.update(id, requestDto);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> delete(@PathVariable(name = "postId") long id) {
        return postService.delete(id);
    }
}
