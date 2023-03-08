package com.example.boardprac.controller;

import com.example.boardprac.auth.UserDetailsImpl;
import com.example.boardprac.domain.User;
import com.example.boardprac.dto.LoginRequestDto;
import com.example.boardprac.dto.SignupRequestDto;
import com.example.boardprac.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignupRequestDto requestDto) {
        return userService.signUp(requestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto requestDto) {
        return userService.login(requestDto);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.logout(userDetails);
    }
}
