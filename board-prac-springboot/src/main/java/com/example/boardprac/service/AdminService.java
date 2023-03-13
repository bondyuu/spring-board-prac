package com.example.boardprac.service;

import com.example.boardprac.auth.UserDetailsImpl;
import com.example.boardprac.domain.Heart;
import com.example.boardprac.domain.Post;
import com.example.boardprac.domain.User;
import com.example.boardprac.dto.AdminMainResponseDto;
import com.example.boardprac.dto.AdminUserResponseDto;
import com.example.boardprac.global.PostStatus;
import com.example.boardprac.global.UserStatus;
import com.example.boardprac.repository.HeartRepository;
import com.example.boardprac.repository.PostRepository;
import com.example.boardprac.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final HeartRepository heartRepository;

    public ResponseEntity<?> getMain(UserDetailsImpl userDetails) {
        String loginUserEmail = userDetails.getUsername();
        Optional<User> optionalLoginUser = userRepository.findByEmail(loginUserEmail);

        if (optionalLoginUser.isEmpty()) {
            return ResponseEntity.badRequest().body("Admin Not Found");
        }

        User loginUser = optionalLoginUser.get();
        if (loginUser.isNotAdmin()) {
            return ResponseEntity.badRequest().body("Only Admin Permitted");
        }

        long userNum = userRepository.countAllByStatus(UserStatus.ACTIVE);
        long postNum = postRepository.countAllByStatus(PostStatus.ACTIVE);

        return ResponseEntity.ok(AdminMainResponseDto.builder()
                                                    .userNum(userNum)
                                                    .postNum(postNum)
                                                    .build());
    }

    public ResponseEntity<?> searchPost(String title, UserDetailsImpl userDetails, Pageable pageable) {
        String loginUserEmail = userDetails.getUsername();
        Optional<User> optionalLoginUser = userRepository.findByEmail(loginUserEmail);

        if (optionalLoginUser.isEmpty()) {
            return ResponseEntity.badRequest().body("Admin Not Found");
        }

        User loginUser = optionalLoginUser.get();
        if (loginUser.isNotAdmin()) {
            return ResponseEntity.badRequest().body("Only Admin Permitted");
        }

        if (title.equals("")) {
            return ResponseEntity.ok(postRepository.findAll(pageable).map(Post::toPostDto));
        }

        return ResponseEntity.ok(postRepository.findAllByTitleContaining(title, pageable).map(Post::toPostDto));
    }

    public ResponseEntity<?> getUserDetail(long id, UserDetailsImpl userDetails) {
        String loginUserEmail = userDetails.getUsername();
        Optional<User> optionalLoginUser = userRepository.findByEmail(loginUserEmail);

        if (optionalLoginUser.isEmpty()) {
            return ResponseEntity.badRequest().body("Admin Not Found");
        }

        User loginUser = optionalLoginUser.get();
        if (loginUser.isNotAdmin()) {
            return ResponseEntity.badRequest().body("Only Admin Permitted");
        }

        User targetUser = userRepository.getById(id);

        List<Post> postList = postRepository.findAllByUser(targetUser);
        List<Post> likeList = heartRepository.findAllByUser(targetUser).stream().map(Heart::getPost).toList();


        return ResponseEntity.ok(AdminUserResponseDto.builder()
                .user(targetUser.toUserDto())
                .postList(postList.stream().map(Post::toPostDto).toList())
                .likeList(likeList.stream().map(Post::toPostDto).toList())
                .build());
    }
}
