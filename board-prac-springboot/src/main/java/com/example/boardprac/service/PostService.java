package com.example.boardprac.service;

import com.example.boardprac.auth.UserDetailsImpl;
import com.example.boardprac.domain.Post;
import com.example.boardprac.domain.User;
import com.example.boardprac.dto.PostDto;
import com.example.boardprac.dto.PostRequestDto;
import com.example.boardprac.repository.PostRepository;
import com.example.boardprac.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

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
        Post post = postRepository.save(Post.builder()
                                            .title(requestDto.getTitle())
                                            .content(requestDto.getContent())
                                            .user(loginUser)
                                            .build());

        return ResponseEntity.ok(post.toPostDto());
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> find(String title, Pageable pageable) {
        Slice<PostDto> postList;
        if (title.equals("")) {
            postList = postRepository.findAll(pageable).map(Post::toPostDto);
        } else {
            postList = postRepository.findAllByTitleContaining(title, pageable).map(Post::toPostDto);
        }
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
