package com.example.boardprac.service;

import com.example.boardprac.auth.UserDetailsImpl;
import com.example.boardprac.domain.Heart;
import com.example.boardprac.domain.Post;
import com.example.boardprac.domain.User;
import com.example.boardprac.dto.PostDto;
import com.example.boardprac.dto.PostRequestDto;
import com.example.boardprac.global.PostStatus;
import com.example.boardprac.global.Role;
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
    public ResponseEntity<?> savePost(PostRequestDto requestDto, UserDetailsImpl userDetails) {
        Optional<User> loginUser = userRepository.findByEmail(userDetails.getUsername());

        if (loginUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Post post = postRepository.save(Post.builder()
                                            .title(requestDto.getTitle())
                                            .content(requestDto.getContent())
                                            .user(loginUser.get())
                                            .status(PostStatus.ACTIVE)
                                            .build());

        return ResponseEntity.ok(post.toPostDto());
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> searchPost(String title, Pageable pageable) {
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
    public ResponseEntity<?> editPost(long id, PostRequestDto requestDto, UserDetailsImpl userDetails) {
        String email = userDetails.getUsername();
        Optional<User> optionalUser = userRepository.findByEmail(email);
        Optional<Post> optionalPost = postRepository.findById(id);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body("User Not Found");
        }
        if (optionalPost.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User loginUser = optionalUser.get();
        Post post = optionalPost.get();

        if (loginUser.canNotControlPost(post)) {
            return ResponseEntity.badRequest().body("Not Permitted");
        }

        post.edit(requestDto);

        return ResponseEntity.ok(post.toPostDto());
    }

    @Transactional
    public ResponseEntity<?> deletePost(long id, UserDetailsImpl userDetails) {
        String email = userDetails.getUsername();
        Optional<User> optionalUser = userRepository.findByEmail(email);
        Optional<Post> optionalPost = postRepository.findById(id);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body("User Not Found");
        }
        if (optionalPost.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User loginUser = optionalUser.get();
        Post post = optionalPost.get();

        if (loginUser.canNotControlPost(post)) {
            return ResponseEntity.badRequest().body("Not Permitted");
        }

        if (loginUser.getRole() == Role.ROLE_ADMIN) {
            post.toBannedPost(PostStatus.BANNED);
        } else {
            post.toDeletedPost(PostStatus.DELETED);
        }
        return ResponseEntity.ok("post deleted");
    }

    @Transactional
    public ResponseEntity<?> likePost(long id, UserDetailsImpl userDetails) {
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

    public ResponseEntity<?> getPostsByUser(long id, UserDetailsImpl userDetails) {
        String loginUserEmail = userDetails.getUsername();
        Optional<User> optionalLoginUser = userRepository.findByEmail(loginUserEmail);

        if (optionalLoginUser.isEmpty()) {
            return ResponseEntity.badRequest().body("Admin Not Found");
        }

        User loginUser = optionalLoginUser.get();
        if (loginUser.isNotAdmin()) {
            return ResponseEntity.badRequest().body("Only Admin Permitted");
        }

        Optional<User> OptionalTargetUser = userRepository.findById(id);
        if (OptionalTargetUser.isEmpty()) {
            return ResponseEntity.badRequest().body("User Not Found");
        }

        User targetUser = OptionalTargetUser.get();

        return ResponseEntity.ok(postRepository.findAllByUser(targetUser).stream().map(Post::toPostDto).toList());
    }
}
