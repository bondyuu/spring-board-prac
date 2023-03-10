package com.example.boardprac.service;

import com.example.boardprac.auth.UserDetailsImpl;
import com.example.boardprac.domain.RefreshToken;
import com.example.boardprac.domain.User;
import com.example.boardprac.dto.*;
import com.example.boardprac.global.Role;
import com.example.boardprac.global.UserStatus;
import com.example.boardprac.jwt.TokenProvider;
import com.example.boardprac.repository.RefreshTokenRepository;
import com.example.boardprac.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public ResponseEntity<?> signUp(SignupRequestDto requestDto) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();

        if (userRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.badRequest().body("Duplicated Email");
        }

        Role role;
        if (email.equals("admin@test.com")){
            role = Role.ROLE_ADMIN;
        } else {
            role = Role.ROLE_USER;
        }

        User user = userRepository.save(User.builder()
                                            .email(email)
                                            .password(bCryptPasswordEncoder.encode(password))
                                            .role(role)
                                            .status(UserStatus.ACTIVE)
                                            .build());
        return ResponseEntity.ok(user.toUserDto());
    }

    @Transactional
    public ResponseEntity<?> login(LoginRequestDto requestDto) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = optionalUser.get();

        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.badRequest().body("Password is not valid");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        TokenDto token = tokenProvider.generateToken(authentication);

        refreshTokenRepository.save(RefreshToken.builder().user(user).token(token.getRefreshToken()).build());
        return ResponseEntity.ok(LoginResponseDto.builder()
                                                .token(token)
                                                .role(user.getRole())
                                                .build());
    }

    @Transactional
    public ResponseEntity<?> logout(UserDetailsImpl userDetails) {
        String email = userDetails.getUsername();

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = optionalUser.get();

        refreshTokenRepository.deleteByUser(user);

        return ResponseEntity.ok("logout");
    }

    public ResponseEntity<?> getUsers(String email, UserDetailsImpl userDetails) {
        String loginUserEmail = userDetails.getUsername();
        Optional<User> optionalLoginUser = userRepository.findByEmail(loginUserEmail);

        if (optionalLoginUser.isEmpty()) {
            return ResponseEntity.badRequest().body("Admin Not Found");
        }

        User loginUser = optionalLoginUser.get();
        if (loginUser.isNotAdmin()) {
            return ResponseEntity.badRequest().body("Only Admin Permitted");
        }

        if (email.equals("")) {
            return ResponseEntity.ok(userRepository.findAll().stream().map(User::toUserDto).toList());
        }

        return ResponseEntity.ok(userRepository.findAllByEmailContaining(email).stream().map(User::toUserDto).toList());
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

        Optional<User> optionalTargetUser = userRepository.findById(id);
        if (optionalTargetUser.isEmpty()) {
            return ResponseEntity.badRequest().body("User Not Found");
        }

        User targetUser = optionalTargetUser.get();

        return ResponseEntity.ok(targetUser);
    }

    @Transactional
    public ResponseEntity<?> deleteUser(long id, UserDetailsImpl userDetails) {
        String email = userDetails.getUsername();

        Optional<User> optionalLoginUser = userRepository.findByEmail(email);
        Optional<User> optionalTargetUser = userRepository.findById(id);

        if (optionalLoginUser.isEmpty()) {
            return ResponseEntity.badRequest().body("loginUser Not Found");
        }
        if (optionalTargetUser.isEmpty()) {
            return ResponseEntity.badRequest().body("targetUser Not Found");
        }

        User loginUser = optionalLoginUser.get();
        User targetUser = optionalTargetUser.get();

        if (loginUser.canNotControlUser(targetUser)) {
            return ResponseEntity.badRequest().body("Not Permitted");
        }


        if (loginUser.getRole().equals(Role.ROLE_ADMIN)) {
            targetUser.toBannedUser(UserStatus.BANNED);

            return ResponseEntity.ok(UserStatus.BANNED.getDesc());
        }

        targetUser.toLeavedUser(UserStatus.LEAVED);
        return ResponseEntity.ok(UserStatus.LEAVED.getDesc());
    }
}
