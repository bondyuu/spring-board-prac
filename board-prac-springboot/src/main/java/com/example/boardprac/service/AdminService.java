package com.example.boardprac.service;

import com.example.boardprac.auth.UserDetailsImpl;
import com.example.boardprac.domain.User;
import com.example.boardprac.dto.AdminMainResponseDto;
import com.example.boardprac.repository.PostRepository;
import com.example.boardprac.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

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

        long userNum = userRepository.count();
        long postNum = postRepository.count();

        return ResponseEntity.ok(AdminMainResponseDto.builder()
                                                    .userNum(userNum)
                                                    .postNum(postNum)
                                                    .build());
    }
}
