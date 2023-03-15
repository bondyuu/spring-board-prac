package com.example.boardprac.repository;

import com.example.boardprac.domain.User;
import com.example.boardprac.global.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Page<User> findAllByEmailContaining(String email, Pageable pageable);
    Page<User> findAll(Pageable pageable);

    long countAllByStatus(UserStatus status);
}
