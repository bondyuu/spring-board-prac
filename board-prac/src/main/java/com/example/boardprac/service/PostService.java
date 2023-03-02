package com.example.boardprac.service;

import com.example.boardprac.auth.UserDetailsImpl;
import com.example.boardprac.domain.Post;
import com.example.boardprac.domain.User;
import com.example.boardprac.dto.PostRequestDto;
import com.example.boardprac.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public ResponseEntity<?> save(PostRequestDto requestDto, UserDetailsImpl userDetails) {
        Post post = Post.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .user(userDetails.getUser())
                .build();

        return ResponseEntity.ok(postRepository.save(post));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> findAll() {
        List<Post> postList = postRepository.findAll();
        return ResponseEntity.ok(postList);
    }

    public ResponseEntity<?> findById(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Not Found")
        );

        return ResponseEntity.ok(post);
    }

    @Transactional
    public ResponseEntity<?> update(long id, PostRequestDto requestDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Not Found")
        );
        post.edit(requestDto);

        return ResponseEntity.ok(post);
    }

    @Transactional
    public ResponseEntity<?> delete(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Not Found")
        );

        postRepository.delete(post);
        return ResponseEntity.ok("post deleted");
    }
}
