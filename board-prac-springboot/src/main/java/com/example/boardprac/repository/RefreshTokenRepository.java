package com.example.boardprac.repository;

import com.example.boardprac.domain.RefreshToken;
import com.example.boardprac.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    void deleteByUser(User user);
}
