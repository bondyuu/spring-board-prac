package com.example.boardprac.service;

import com.example.boardprac.auth.UserDetailsImpl;
import com.example.boardprac.domain.RefreshToken;
import com.example.boardprac.domain.User;
import com.example.boardprac.dto.LoginRequestDto;
import com.example.boardprac.dto.SignupRequestDto;
import com.example.boardprac.dto.TokenDto;
import com.example.boardprac.dto.UserDto;
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

        User user = userRepository.save(User.builder()
                                            .email(email)
                                            .password(bCryptPasswordEncoder.encode(password))
                                            .role(User.Role.ROLE_USER)
                                            .build());
        return ResponseEntity.ok(UserDto.builder()
                                        .id(user.getId())
                                        .email(user.getEmail())
                                        .build());
    }

    @Transactional
    public ResponseEntity<?> login(LoginRequestDto requestDto) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("Not Found")
        );

        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.badRequest().body("Password is not valid");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        TokenDto token = tokenProvider.generateToken(authentication);

        refreshTokenRepository.save(RefreshToken.builder().user(user).token(token.getRefreshToken()).build());
        return ResponseEntity.ok(token);
    }

    @Transactional
    public ResponseEntity<?> logout(UserDetailsImpl userDetails) {
        String email = userDetails.getUsername();

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("Not Found")
        );

        refreshTokenRepository.deleteByUser(user);

        return ResponseEntity.ok("logout");
    }

}
