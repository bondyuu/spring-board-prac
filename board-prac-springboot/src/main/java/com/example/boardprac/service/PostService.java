package com.example.boardprac.service;

import com.example.boardprac.auth.UserDetailsImpl;
import com.example.boardprac.domain.Post;
import com.example.boardprac.domain.User;
import com.example.boardprac.dto.PostDto;
import com.example.boardprac.dto.PostRequestDto;
import com.example.boardprac.repository.PostRepository;
import com.example.boardprac.repository.UserRepository;
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
    private final UserRepository userRepository;

    @Transactional
    public ResponseEntity<?> save(PostRequestDto requestDto, UserDetailsImpl userDetails) {
        User loginUser = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("Not Found")
        );
        System.out.println(loginUser.getId());
        Post post = postRepository.save(Post.builder()
                                            .title(requestDto.getTitle())
                                            .content(requestDto.getContent())
                                            .user(loginUser)
                                            .build());

        return ResponseEntity.ok(post.toPostDto());
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> findAll() {
        List<PostDto> postList = postRepository.findAll().stream().map(Post::toPostDto).toList();
        return ResponseEntity.ok(postList);
    }

    @Transactional(readOnly = true)
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

        return ResponseEntity.ok(post.toPostDto());
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
