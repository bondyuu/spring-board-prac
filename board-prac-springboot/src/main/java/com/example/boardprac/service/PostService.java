package com.example.boardprac.service;

import com.example.boardprac.auth.UserDetailsImpl;
import com.example.boardprac.domain.Heart;
import com.example.boardprac.domain.Post;
import com.example.boardprac.domain.User;
import com.example.boardprac.dto.PostDto;
import com.example.boardprac.dto.PostRequestDto;
import com.example.boardprac.repository.HeartRepository;
import com.example.boardprac.repository.PostRepository;
import com.example.boardprac.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final HeartRepository heartRepository;

    @Transactional
    public ResponseEntity<?> save(PostRequestDto requestDto, UserDetailsImpl userDetails) {
        Optional<User> loginUser = userRepository.findByEmail(userDetails.getUsername());

        if (loginUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Post post = postRepository.save(Post.builder()
                                            .title(requestDto.getTitle())
                                            .content(requestDto.getContent())
                                            .user(loginUser.get())
                                            .build());

        return ResponseEntity.ok(post.toPostDto());
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> search(String title, Pageable pageable) {
        if (title.equals("")) {
            return ResponseEntity.ok(postRepository.findAll(pageable).map(Post::toPostDto));
        }

        return ResponseEntity.ok(postRepository.findAllByTitleContaining(title, pageable).map(Post::toPostDto));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> findById(long id) {
        Optional<Post> optionalPost = postRepository.findById(id);

        if (optionalPost.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(optionalPost.get());
    }

    @Transactional
    public ResponseEntity<?> update(long id, PostRequestDto requestDto) {
        Optional<Post> optionalPost = postRepository.findById(id);

        if (optionalPost.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Post post = optionalPost.get();

        post.edit(requestDto);

        return ResponseEntity.ok(post.toPostDto());
    }

    @Transactional
    public ResponseEntity<?> delete(long id) {
        Optional<Post> optionalPost = postRepository.findById(id);

        if (optionalPost.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Post post = optionalPost.get();

        postRepository.delete(post);
        return ResponseEntity.ok("post deleted");
    }

    @Transactional
    public ResponseEntity<?> like(long id, UserDetailsImpl userDetails) {
        Optional<User> optionalUser = userRepository.findByEmail(userDetails.getUsername());
        Optional<Post> optionalPost = postRepository.findById(id);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (optionalPost.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = optionalUser.get();
        Post post = optionalPost.get();

        Optional<Heart> heart = heartRepository.findByUserAndPost(user, post);

        if (heart.isEmpty()) {
            heartRepository.save(Heart.builder()
                                        .user(user)
                                        .post(post)
                                        .build());

            return ResponseEntity.ok("좋아요 성공");
        }
        heartRepository.delete(heart.get());

        return ResponseEntity.ok("좋아요 취소");
    }
}
