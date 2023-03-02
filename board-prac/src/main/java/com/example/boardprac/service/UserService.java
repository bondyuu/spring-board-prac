package com.example.boardprac.service;

import com.example.boardprac.domain.User;
import com.example.boardprac.dto.SignupRequestDto;
import com.example.boardprac.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public ResponseEntity<?> signUp(SignupRequestDto requestDto) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();

        if (userRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.badRequest().body("Duplicated Email");
        }

        User user = User.builder()
                        .email(email)
                        .password(bCryptPasswordEncoder.encode(password))
                        .role(User.Role.ROLE_USER)
                        .build();
        return ResponseEntity.ok(userRepository.save(user));
    }

}
